package com.mf.domain.di

import com.mf.domain.useCase.navigationUseCase.INavigationUseCase
import com.mf.domain.useCase.navigationUseCase.NavigationUseCase
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class UseCaseModule {
    @Binds
    @Singleton
    abstract fun naviUseCase(naviUseCase: NavigationUseCase): INavigationUseCase
}