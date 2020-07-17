package com.mf.compass.presentation.compass

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.mf.compass.presentation.shared.CompassViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

     val viewModel: CompassViewModel by activityViewModels{
        viewModelFactory
    }
}