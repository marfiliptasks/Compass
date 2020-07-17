package com.mf.data.dataSources

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.mf.domain.consts.FILTERING_FACTOR
import com.mf.domain.dataSources.IPositionDataSource
import com.mf.domain.model.dataSourcesModels.PositionDataModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


class PositionDataSource @Inject constructor(
    private val sensorManager: SensorManager
) : IPositionDataSource, SensorEventListener {
    private val rotationMatrix = FloatArray(9)
    private var lastMagnetometer = FloatArray(3)
    private var lastAccelerometer = FloatArray(3)
    private var rotationMeterAvailable = false
    private var orientation = FloatArray(3)

    @ExperimentalCoroutinesApi
    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_ROTATION_VECTOR -> {
                SensorManager.getRotationMatrixFromVector(
                    rotationMatrix,
                    event.values
                )
            }
            Sensor.TYPE_MAGNETIC_FIELD -> {
                lastMagnetometer = event.values.rampValues(lastMagnetometer)
            }
            Sensor.TYPE_ACCELEROMETER -> {
                lastAccelerometer = event.values.rampValues(lastAccelerometer)
            }
        }
        emitData(rotationMeterAvailable)
    }

    @ExperimentalCoroutinesApi
    private fun emitData(rotationMeterAvailable: Boolean) {
        if (!rotationMeterAvailable) {
            SensorManager.getRotationMatrix(
                rotationMatrix,
                null,
                lastAccelerometer,
                lastMagnetometer
            )
        }
        dataSourceResult.value = PositionDataModel(
            SensorManager.getOrientation(rotationMatrix, orientation)[0]
        )
    }

    private fun registerSensorListener(sensorType: Int): Boolean =
        with(sensorManager) {
            getDefaultSensor(sensorType)?.let {
                registerListener(
                    this@PositionDataSource, it, SensorManager.SENSOR_DELAY_UI
                )
            } ?: false
        }

    override fun register() =
        if (registerSensorListener(Sensor.TYPE_ROTATION_VECTOR)) {
            rotationMeterAvailable = true
            true
        } else {
            listOf(Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_MAGNETIC_FIELD).all {
                registerSensorListener(it)
            }
        }

    @ExperimentalCoroutinesApi
    override val dataSourceResult: MutableStateFlow<PositionDataModel?> =
        MutableStateFlow(null)

    override fun unregister() {
        sensorManager.unregisterListener(this)
    }

    private fun FloatArray.rampValues(_previousValues: FloatArray) =
        mapIndexed { index: Int, value: Float ->
            value * FILTERING_FACTOR + _previousValues[index] * (1.0f - FILTERING_FACTOR)
        }.toFloatArray()

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //do nothing
    }
}