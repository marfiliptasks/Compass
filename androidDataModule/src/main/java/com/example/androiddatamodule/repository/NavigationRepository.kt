package com.example.androiddatamodule.repository

import com.example.androiddatamodule.dataSources.LocationDataSource
import com.example.androiddatamodule.dataSources.PositionDataSource
import com.example.domain.dataSources.ILocationDataSource
import com.example.domain.dataSources.IPositionDataSource
import com.example.domain.model.*
import com.example.domain.model.dataSourcesModels.LocationDataModel
import com.example.domain.model.dataSourcesModels.PositionDataModel
import com.example.domain.repository.INavigationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class NavigationRepository @Inject constructor(
    private val locationDataSource: ILocationDataSource,
    private val positionDataSource: IPositionDataSource
) : INavigationRepository {

    private var cachedLocationModel: LocationDataModel? = null
    private var cachedPositionDataModel: PositionDataModel =
        PositionDataModel(0f)

    @FlowPreview
    override suspend fun observePosition() = flow {
        flowOf(locationDataSource.dataSourceResult, positionDataSource.dataSourceResult)
            .flattenMerge().collect { data ->
                when (data) {
                    is PositionDataModel -> {
                        cachedPositionDataModel = data
                    }
                    is LocationDataModel -> {
                        cachedLocationModel = data
                    }
                }
                emit(listOfNotNull(cachedPositionDataModel, cachedLocationModel))
            }
    }

    override fun setCoordinates(cords: CordsModel) =
        locationDataSource.setTargetLocation(cords)

    override fun unregisterListeners() =
        listOf(locationDataSource, positionDataSource).forEach { it.unregister() }

    @ExperimentalCoroutinesApi
    override fun registerListeners(location: Boolean, position: Boolean) =
        listOf(locationDataSource, positionDataSource).filter {
            (it is PositionDataSource && position) || (it is LocationDataSource && location)
        }.all {
            it.register()
        }
}





