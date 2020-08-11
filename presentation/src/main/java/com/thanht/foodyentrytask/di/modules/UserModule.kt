package com.thanht.foodyentrytask.di.modules

import com.thanht.data.city.CityRepositoryImpl
import com.thanht.data.login.LoginRepositoryImpl
import com.thanht.domain.base.TaskSchedulers
import com.thanht.domain.city.CityRepository
import com.thanht.domain.login.LoginRepository
import com.thanht.foodyentrytask.TaskSchedulersImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class UserModule {

    // Task
    @Binds
    @Singleton
    abstract fun bindTaskSchedulers(taskSchedulersImpl: TaskSchedulersImpl): TaskSchedulers

    // repository
    @Binds
    abstract fun bindLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun bindCityRepository(cityRepositoryImpl: CityRepositoryImpl): CityRepository
}