package com.mf.compass.di

import com.mf.compass.presentation.MainActivity
import com.mf.data.dataSources.ProvidersChangedReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contribureRecevier(): ProvidersChangedReceiver
}