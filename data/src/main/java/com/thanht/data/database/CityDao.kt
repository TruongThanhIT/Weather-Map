package com.thanht.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Query("SELECT * FROM city")
    suspend fun getAll(): List<CityEntity>

    @Query("SELECT * FROM city WHERE city_name LIKE :keyword")
    fun searchCity(keyword: String): Flow<List<CityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cityEntity: List<CityEntity>): Completable
}