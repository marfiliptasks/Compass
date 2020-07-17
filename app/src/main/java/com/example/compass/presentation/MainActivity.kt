package com.example.compass.presentation

import android.content.IntentFilter
import android.location.LocationManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.TooltipCompat
import com.example.compass.R
import com.example.compass.presentation.coordsDialog.CordsDialogFragment
import com.example.androiddatamodule.dataSources.ProvidersChangedReceiver
import com.example.domain.model.eventModels.ProvidersChangedEvent
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jakewharton.rxbinding4.view.clicks
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.concurrent.TimeUnit


class MainActivity : DaggerAppCompatActivity() {

    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TooltipCompat.setTooltipText(fab, getString(R.string.setup_coords))
        setupEvents()
    }

    private fun setupEvents() {
        registerReceiver(
            ProvidersChangedReceiver(),
            IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        )
        fab.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe {
            CordsDialogFragment().show(supportFragmentManager, null)
        }
    }

    @Subscribe
    fun onProvidersChangedEvent(event: ProvidersChangedEvent) {
        EventBus.getDefault().unregister(event)
        if (ProvidersChangedReceiver.shouldShowAtm) {
            if (event.show && dialog?.isShowing != true) {
                dialog = MaterialAlertDialogBuilder(this).setTitle(R.string.warning)
                    .setMessage(R.string.location_services_disabled)
                    .setPositiveButton(R.string.ok) { _, _ ->
                    }.show().also {
                        it.setOnDismissListener {
                            dialog = null
                        }
                    }
            } else if (!event.show) {
                dialog?.dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}