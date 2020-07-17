package com.mf.data.di

import com.mf.data.repository.NavigationRepository
import com.mf.domain.repository.INavigationRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun naviRepository(naviRepository: NavigationRepository): INavigationRepository


}