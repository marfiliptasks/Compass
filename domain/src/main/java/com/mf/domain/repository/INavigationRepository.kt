package com.mf.domain.repository

import com.mf.domain.model.CordsModel
import com.mf.domain.model.dataSourcesModels.IDataSourceModel
import kotlinx.coroutines.flow.Flow

interface INavigationRepository {
    fun setCoordinates(cords: CordsModel)
    fun unregisterListeners()
    fun registerListeners(location: Boolean, position: Boolean): Boolean
    suspend fun observePosition(): Flow<List<IDataSourceModel>>
}