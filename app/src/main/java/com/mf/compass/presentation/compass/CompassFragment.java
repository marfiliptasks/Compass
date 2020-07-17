package com.mf.compass.presentation.compass;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.mf.data.dataSources.ProvidersChangedReceiver;
import com.mf.compass.R;
import com.mf.domain.model.DegreesResultModel;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import kotlin.Pair;

import static com.mf.compass.uiHelpers.BackgroundColorUpdaterKt.updateBackgroundColor;
import static com.mf.domain.consts.LocationConfigKt.DEFAULT_PIVOT;
import static com.mf.domain.consts.RequestCodesKt.REQUEST_PERM_CODE;

public class CompassFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.compass_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setArrowHeight();
        super.onViewCreated(view, savedInstanceState);
        setupObservers();
    }

    private void setArrowHeight() {
        if (getView() != null && getActivity() != null) {
            View destinationArrow = getView().findViewById(R.id.compass_fragment_arrow);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            ViewGroup.LayoutParams params = destinationArrow.getLayoutParams();
            params.height = displayMetrics.widthPixels;
            destinationArrow.setLayoutParams(params);
        }
    }

    private void setupObservers() {
        getViewModel().getRotation().observe(getViewLifecycleOwner(), this::animateTargets);
        getViewModel().getSetupListenersSuccess().observe(getViewLifecycleOwner(), result -> {
            if (!result) {
                showFailedListenersSetup();
            }
        });
        getViewModel().getCords().observe(getViewLifecycleOwner(), cords ->
                startLocationListenerSafe()
        );
    }

    private void animateTargets(Pair<DegreesResultModel, DegreesResultModel> rotationData) {
        if (getView() != null) {

            ImageView compassImage = getView().findViewById(R.id.compass_fragment_compass);
            animateTarget(
                    compassImage, rotationData.getFirst().getAzimuthDegrees(),
                    rotationData.getSecond().getAzimuthDegrees()
            );

            View destinationArrow = getView().findViewById(R.id.compass_fragment_arrow);
            animateTarget(
                    destinationArrow, rotationData.getFirst().getTargetDegrees(),
                    rotationData.getSecond().getTargetDegrees()
            );

            if (getContext() != null) {
                updateBackgroundColor(rotationData.getSecond().getTargetDegrees(), getContext(), getView());
            }
        }
    }

    private void animateTarget(
            @NotNull View targetView,
            @NotNull Float fromDegrees,
            @NotNull Float toDegrees) {
        RotateAnimation rotateAnimation = new RotateAnimation(
                fromDegrees,
                toDegrees,
                Animation.RELATIVE_TO_SELF,
                DEFAULT_PIVOT,
                Animation.RELATIVE_TO_SELF,
                DEFAULT_PIVOT
        );

        rotateAnimation.setDuration(210);
        rotateAnimation.setFillAfter(false);
        targetView.startAnimation(rotateAnimation);
    }

    private void startLocationListenerSafe() {
        if (!checkLocPermissions()) {
            String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions, REQUEST_PERM_CODE);
        } else {
            getViewModel().startListeners(true, false);
        }
    }

    private boolean checkLocPermissions() {
        if (getContext() != null) {
            return ActivityCompat.checkSelfPermission(
                    getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED;
        } else {
            return false;
        }
    }

    private void showFailedListenersSetup() {
        if (getView() != null)
            Snackbar.make(getView(), R.string.listeners_registering_fail, Snackbar.LENGTH_LONG)
                    .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getViewModel().startListeners(true, false);
            }
        }
    }

    @Override
    public void onPause() {
        ProvidersChangedReceiver.Companion.setServiceStarted(false);
        getViewModel().stopListeners();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getViewModel().getCords().getValue() != null) {
            startLocationListenerSafe();
        }
        getViewModel().startListeners(false, true);
    }
}
