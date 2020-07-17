package com.example.domain.dataSources

import com.example.domain.model.dataSourcesModels.IDataSourceModel
import kotlinx.coroutines.flow.Flow

interface IAndroidDataSource {
    fun register() : Boolean
    val dataSourceResult : Flow<IDataSourceModel?>
    fun unregister()
}