package com.thanht.foodyentrytask.di.modules

import android.app.Application
import android.content.Context
import com.thanht.data.city.CityDataSource
import com.thanht.data.login.LoginDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Provides
    fun provideLoginDataSource(): LoginDataSource {
        return LoginDataSource()
    }

    @Provides
    fun provideCityDataSource(@ApplicationContext context: Context): CityDataSource {
        return CityDataSource(context)
    }

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope{
        return CoroutineScope(Dispatchers.IO)
    }
}