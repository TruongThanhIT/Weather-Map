package com.thanht.domain.city

import com.thanht.domain.database.DatabaseManager
import com.thanht.domain.model.CityModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn


class SearchCityListUseCase @Inject constructor(private val databaseManager: DatabaseManager) {

    fun searchCity(keyword: String): Flow<List<CityModel>> {
        return databaseManager.searchCity("%${keyword}%")
            .catch { _ -> emit(emptyList()) }
            .flowOn(Dispatchers.IO)
    }
}