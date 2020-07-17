package com.mf.compass.di

import com.mf.compass.presentation.compass.CompassFragment
import com.mf.compass.presentation.coordsDialog.CordsDialogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeCompassFragment(): CompassFragment

    @ContributesAndroidInjector
    abstract fun contributeCordsDialogFragment(): CordsDialogFragment
}