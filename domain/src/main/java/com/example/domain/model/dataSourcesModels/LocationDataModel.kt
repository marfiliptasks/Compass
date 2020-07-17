package com.example.domain.model.dataSourcesModels

data class LocationDataModel(
    override val degreeValue: Float,
    val declination: Float
) : IDataSourceModel