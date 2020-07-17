package com.mf.compass.di

import android.app.Application
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Context.SENSOR_SERVICE
import android.hardware.SensorManager
import android.location.LocationManager
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun context(app: Application): Context = app

    @Provides
    fun sensorManager(context: Context): SensorManager =
        context.getSystemService(SENSOR_SERVICE) as SensorManager

    @Provides
    fun locationManager(context: Context): LocationManager =
        context.getSystemService(LOCATION_SERVICE) as LocationManager
}