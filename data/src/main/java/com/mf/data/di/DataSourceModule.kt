package com.mf.data.di

import com.mf.data.dataSources.LocationDataSource
import com.mf.data.dataSources.PositionDataSource
import com.mf.domain.dataSources.ILocationDataSource
import com.mf.domain.dataSources.IPositionDataSource
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