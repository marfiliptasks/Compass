package com.example.compass.presentation.coordsDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.compass.R
import com.example.compass.presentation.shared.CompassViewModel
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.cords_dialog.*
import util.afterTextChanged
import javax.inject.Inject

class CordsDialogFragment : DaggerDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.cords_dialog, container, true)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: CompassViewModel by activityViewModels{
        viewModelFactory
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.attributes = dialog?.window?.attributes?.apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
        setupEvents()
    }

    private fun setupEvents() {
        cords_dialog_longitude_value.afterTextChanged {
            cords_dialog_apply.isEnabled = it.isNotEmpty()
                    && cords_dialog_latitude_value.text?.isEmpty() == false
        }
        cords_dialog_latitude_value.afterTextChanged {
            cords_dialog_apply.isEnabled = it.isNotEmpty()
                    && cords_dialog_longitude_value.text?.isEmpty() == false
        }
        cords_dialog_apply.setOnClickListener {
            viewModel.startNavigation(
                cords_dialog_longitude_value.text.toString(),
                cords_dialog_latitude_value.text.toString()
            )
            dismiss()
        }
    }
}