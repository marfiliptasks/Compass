package com.example.compass.di

import com.example.compass.presentation.MainActivity
import com.example.androiddatamodule.dataSources.ProvidersChangedReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contribureRecevier(): ProvidersChangedReceiver
}