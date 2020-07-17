package com.mf.domain.converters

import com.mf.domain.model.DegreesResultModel
import com.mf.domain.model.dataSourcesModels.IDataSourceModel
import com.mf.domain.model.dataSourcesModels.LocationDataModel
import com.mf.domain.model.dataSourcesModels.PositionDataModel

object DegreeConverter {

    fun convertToDegrees(data: List<IDataSourceModel>): DegreesResultModel {
        val position = calculateAzimuth(data.find { it is PositionDataModel } as PositionDataModel?)
        val location = data.find { it is LocationDataModel } as LocationDataModel?
        return DegreesResultModel(
            position,
            calculateHeading(location, position)
        )
    }

    private fun calculateAzimuth(positionData: PositionDataModel?): Float =
        positionData?.let {
            -Math.toDegrees(it.degreeValue.toDouble()).toFloat()
        } ?: Float.NaN

    private fun calculateHeading(
        location: LocationDataModel?,
        position: Float
    ): Float = location?.let {
        (it.degreeValue + position + it.declination)
    } ?: Float.NaN
}


