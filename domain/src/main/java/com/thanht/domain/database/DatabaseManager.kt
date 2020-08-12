package com.thanht.domain.database

import com.thanht.domain.model.CityModel
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface DatabaseManager {
    fun searchCity(keyword: String): Flow<List<CityModel>>

    fun saveCityList(cityModels: List<CityModel>): Completable
}