package com.mf.compass.di

import android.app.Application
import com.mf.data.di.DataSourceModule
import com.mf.data.di.RepositoryModule
import com.mf.compass.AppMain
import com.mf.domain.di.UseCaseModule
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