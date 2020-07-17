package com.example.androiddatamodule.di

import com.example.androiddatamodule.repository.NavigationRepository
import com.example.domain.repository.INavigationRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun naviRepository(naviRepository: NavigationRepository): INavigationRepository


}