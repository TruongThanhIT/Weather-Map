package com.thanht.domain.city

import com.thanht.domain.database.DatabaseManager
import com.thanht.domain.model.CityModel
import javax.inject.Inject

class SearchCityListUseCase @Inject constructor(private val databaseManager: DatabaseManager) {

    suspend fun searchCity(keyword: String): List<CityModel> {
        return databaseManager.searchCity("%${keyword}%")
    }
}