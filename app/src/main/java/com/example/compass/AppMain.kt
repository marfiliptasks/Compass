package com.example.compass

import com.example.compass.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class AppMain : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().application(this).build()
}