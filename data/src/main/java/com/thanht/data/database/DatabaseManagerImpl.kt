package com.thanht.data.database

import android.content.Context
import androidx.room.Room
import com.thanht.domain.database.DatabaseManager
import com.thanht.domain.model.CityModel
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatabaseManagerImpl @Inject constructor(context: Context) : DatabaseManager {
    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "weather-map-db"
    ).build()

    private val cityDao = database.cityDao()

    override fun searchCity(keyword: String): Flow<List<CityModel>> {
        return cityDao.searchCity(keyword).map { cityList ->
            cityList.map { CityModel(it.id.toLong(), it.cityName) }
        }
    }

    override fun saveCityList(cityModels: List<CityModel>): Completable {
        return cityDao.insertAll(cityModels.map { CityEntity(it.id.toInt(), it.name) })
    }
}