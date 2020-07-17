package com.mf.domain.converters

import com.mf.domain.model.DegreesResultModel
import com.mf.domain.model.dataSourcesModels.IDataSourceModel
import com.mf.domain.model.dataSourcesModels.LocationDataModel
import com.mf.domain.model.dataSourcesModels.PositionDataModel
import org.junit.Test

class DegreeConverterTest {
    private val locationModel = LocationDataModel(36.91189f, 13.13674f)
    private val positionModel = PositionDataModel(-2.1352017f)
    private val expectedAzimuthDegrees = 122.33804f
    private val expectedTargetDegrees = 172.38667f

    @Test
    fun `convert To Degrees Normal Case`() =
        assert(
            DegreesResultModel(
                azimuthDegrees = expectedAzimuthDegrees,
                targetDegrees = expectedTargetDegrees
            ) == DegreeConverter.convertToDegrees(listOf(locationModel, positionModel))
        )

    @Test
    fun `convert To Degrees Single Item Case`() =
        assert(
            testOneItemCase(listOf(locationModel)) && testOneItemCase(listOf(positionModel))
        )

    private fun testOneItemCase(list: List<IDataSourceModel>): Boolean =
        DegreeConverter.convertToDegrees(list) == if (list.first() is LocationDataModel) DegreesResultModel(
            azimuthDegrees = Float.NaN,
            targetDegrees = Float.NaN
        ) else DegreesResultModel(
            azimuthDegrees = expectedAzimuthDegrees,
            targetDegrees = Float.NaN
        )

    @Test
    fun `convert To Degrees Empty List Case`() =
        assert(
            DegreeConverter.convertToDegrees(emptyList()) == DegreesResultModel(
                Float.NaN,
                Float.NaN
            )
        )
}