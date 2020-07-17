package com.example.compass.di

import android.app.Application
import com.example.androiddatamodule.di.DataSourceModule
import com.example.androiddatamodule.di.RepositoryModule
import com.example.compass.AppMain
import com.example.domain.di.UseCaseModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidInjectionModule::class,
        ActivityBuilderModule::class,
        FragmentBuilderModule::class,
        ViewModelModule::class,
        UseCaseModule::class,
        RepositoryModule::class,
        DataSourceModule::class
    ]
)

interface AppComponent : AndroidInjector<AppMain> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}