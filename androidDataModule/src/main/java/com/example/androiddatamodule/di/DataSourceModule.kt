package com.example.androiddatamodule.di

import com.example.androiddatamodule.dataSources.LocationDataSource
import com.example.androiddatamodule.dataSources.PositionDataSource
import com.example.domain.dataSources.ILocationDataSource
import com.example.domain.dataSources.IPositionDataSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun locationDataSource(locationDataSource: LocationDataSource): ILocationDataSource

    @Binds
    @Singleton
    abstract fun positionDataSource(positionDataSource: PositionDataSource): IPositionDataSource
}