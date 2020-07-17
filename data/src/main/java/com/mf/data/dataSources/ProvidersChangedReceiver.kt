package com.mf.data.dataSources

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import com.mf.domain.model.eventModels.ProvidersChangedEvent
import dagger.android.DaggerBroadcastReceiver
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class ProvidersChangedReceiver : DaggerBroadcastReceiver() {

    @Inject
    lateinit var locationManager: LocationManager

    companion object {
        var shouldShowAtm = false
        var serviceStarted = false
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action.equals(LocationManager.PROVIDERS_CHANGED_ACTION)) {
            shouldShowAtm = if (locationManager.getProviders(true).isEmpty()) {
                if(serviceStarted){
                    EventBus.getDefault().post(ProvidersChangedEvent())
                }
                serviceStarted
            } else {
                EventBus.getDefault().post(ProvidersChangedEvent(false))
                false
            }
        }
    }
}