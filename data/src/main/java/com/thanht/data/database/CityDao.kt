package com.thanht.data.database

import androidx.room.*
import com.thanht.data.database.CityEntity
import com.thanht.domain.model.CityModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CityDao {
    @Query("SELECT * FROM city")
    suspend fun getAll(): List<CityEntity>

    @Query("SELECT * FROM city WHERE city_name LIKE :keyword")
    suspend fun searchCity(keyword: String): List<CityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cityEntity: List<CityEntity>): Completable
}