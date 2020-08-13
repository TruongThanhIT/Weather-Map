package com.thanht.foodyentrytask.di.components

import android.app.Application
import com.thanht.data.cache.DataCache
import com.thanht.data.city.CityDataSource
import com.thanht.data.login.LoginDataSource
import com.thanht.domain.database.DatabaseManager
import com.thanht.foodyentrytask.di.modules.AppModule
import com.thanht.foodyentrytask.di.modules.DataStoredModule
import com.thanht.foodyentrytask.di.scopes.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineScope

@ApplicationScope
@Component(modules = [AppModule::class, DataStoredModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    // data source
    fun provideLoginDataSource(): LoginDataSource

    fun provideCityDataSource(): CityDataSource

    fun provideApplication(): Application

    fun provideDataCache(): DataCache

    fun provideDatabaseManager(): DatabaseManager

    fun provideCoroutineScope(): CoroutineScope
}