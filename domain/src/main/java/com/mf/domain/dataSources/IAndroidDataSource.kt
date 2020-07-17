package com.mf.domain.dataSources

import com.mf.domain.model.dataSourcesModels.IDataSourceModel
import kotlinx.coroutines.flow.Flow

interface IAndroidDataSource {
    fun register() : Boolean
    val dataSourceResult : Flow<IDataSourceModel?>
    fun unregister()
}