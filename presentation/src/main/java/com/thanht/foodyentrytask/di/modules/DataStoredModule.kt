package com.thanht.foodyentrytask.di.modules

import android.app.Application
import android.content.Context
import com.thanht.data.cache.DataCache
import com.thanht.data.database.DatabaseManagerImpl
import com.thanht.domain.database.DatabaseManager
import com.thanht.foodyentrytask.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DataStoredModule {

    @Provides
    @Singleton
    fun provideDataCache(@ApplicationContext context: Context): DataCache = DataCache(context)

    @Provides
    @Singleton
    fun provideDatabaseManager(@ApplicationContext context: Context): DatabaseManager {
        return DatabaseManagerImpl(context)
    }
}