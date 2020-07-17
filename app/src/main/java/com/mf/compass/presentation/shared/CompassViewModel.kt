package com.mf.compass.presentation.shared

import androidx.lifecycle.*
import com.mf.data.dataSources.ProvidersChangedReceiver
import com.mf.domain.model.CordsModel
import com.mf.domain.model.DegreesResultModel
import com.mf.domain.useCase.navigationUseCase.INavigationUseCase
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class CompassViewModel @Inject constructor(
    private val navigationUseCase: INavigationUseCase
) : ViewModel() {
    private var _cords: MutableLiveData<CordsModel> = MutableLiveData()
    val cords: LiveData<CordsModel> = _cords
    private var previousDegrees: DegreesResultModel = DegreesResultModel(0f, 0f)
    private var _setupListenersSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val setupListenersSuccess: LiveData<Boolean> = _setupListenersSuccess

    fun startNavigation(lon: String?, lat: String?) {
        if (lon != null && lat != null) {
            CordsModel(lon.toDouble(), lat.toDouble()).also {
                navigationUseCase.setCoordinates(it)
                _cords.value = it
            }
        }
    }

    fun startListeners(location: Boolean, position: Boolean) {
        _setupListenersSuccess.value = navigationUseCase.setupListeners(location, position).also {
            ProvidersChangedReceiver.serviceStarted = location
        }
    }

    val rotation = liveData {
        navigationUseCase.observePositionChanges().collect {
            emit(Pair(previousDegrees, it))
            previousDegrees = it
        }
    }

    fun stopListeners() = navigationUseCase.stopListeners()
}