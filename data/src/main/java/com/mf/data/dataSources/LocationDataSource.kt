package com.mf.data.dataSources

import android.content.Context
import android.hardware.GeomagneticField
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import com.mf.domain.consts.MIN_LOC_DISTANCE
import com.mf.domain.consts.MIN_LOC_TIME
import com.mf.domain.dataSources.ILocationDataSource
import com.mf.domain.model.CordsModel
import com.mf.domain.model.dataSourcesModels.LocationDataModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class LocationDataSource @Inject constructor(
    private val locationManager: LocationManager,
    private val context: Context
) : ILocationDataSource, LocationListener {

    private var targetLocation: Location = Location(LocationManager.GPS_PROVIDER)
    private var myLocation: Location? = null

    override fun onLocationChanged(location: Location?) {
        myLocation = location
        updateLocationValue()
    }

    private fun updateLocationValue() {
        dataSourceResult.value = myLocation?.let {
            LocationDataModel(
                it.bearingTo(
                    targetLocation
                ), declination = getDeclination(it)
            )
        }
    }

    private fun getDeclination(location: Location) = with(location) {
        GeomagneticField(
            latitude.toFloat(),
            longitude.toFloat(),
            altitude.toFloat(),
            System.currentTimeMillis()
        ).declination
    }

    override fun setTargetLocation(cords: CordsModel) {
        targetLocation.apply {
            latitude = cords.lat
            longitude = cords.lon
        }
        updateLocationValue()
    }

    override fun register(): Boolean {
        return when {
            locationManager.getProviders(true).isEmpty() -> {
                false
            }
            checkPlayServices() -> {
                requestUpdatesPlayService()
            }
            else -> {
                requestUpdatesNoPlayServices()
            }
        }
    }

    private fun requestUpdatesPlayService(): Boolean = try {
        LocationServices.getFusedLocationProviderClient(context).requestLocationUpdates(
            LocationRequest().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = MIN_LOC_TIME
                fastestInterval = MIN_LOC_TIME - 200
            },
            locationCallback,
            Looper.getMainLooper()
        )
        true
    } catch (ex: SecurityException) {
        false
    }

    private fun requestUpdatesNoPlayServices(): Boolean = try {
        locationManager.getBestProvider(
            Criteria().apply { isSpeedRequired = false }, true
        )?.let {
            locationManager.requestLocationUpdates(
                it, MIN_LOC_TIME, MIN_LOC_DISTANCE, this
            )
            true
        } ?: false
    } catch (ex: SecurityException) {
        false
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(lr: LocationResult?) {
            super.onLocationResult(lr)
            lr?.let { onLocationChanged(lr.lastLocation) }
        }
    }

    override val dataSourceResult: MutableStateFlow<LocationDataModel?> =
        MutableStateFlow(null)

    override fun unregister() {
        LocationServices.getFusedLocationProviderClient(context)
            .removeLocationUpdates(locationCallback)
        locationManager.removeUpdates(this)
    }

    private fun checkPlayServices(): Boolean =
        (GoogleApiAvailability.getInstance()
            .isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS)

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        //do nothing
    }

    override fun onProviderEnabled(provider: String?) {
        //do nothing
    }

    override fun onProviderDisabled(provider: String?) {
        //do nothing
    }
}