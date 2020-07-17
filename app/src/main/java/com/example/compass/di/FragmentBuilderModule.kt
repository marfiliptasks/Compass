package com.example.compass.di

import com.example.compass.presentation.compass.CompassFragment
import com.example.compass.presentation.coordsDialog.CordsDialogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeCompassFragment(): CompassFragment

    @ContributesAndroidInjector
    abstract fun contributeCordsDialogFragment(): CordsDialogFragment
}