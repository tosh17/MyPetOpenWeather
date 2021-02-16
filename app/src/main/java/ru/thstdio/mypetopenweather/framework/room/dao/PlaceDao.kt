package ru.thstdio.mypetopenweather.framework.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.thstdio.mypetopenweather.framework.room.AppDbContract
import ru.thstdio.mypetopenweather.framework.room.entity.PlaceDBO

@Dao
interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: PlaceDBO): Long

    @Query("SELECT * FROM ${AppDbContract.PlaceEntity.TABLE_NAME} ")
    fun getAllAsFlow(): Flow<List<PlaceDBO>>

    @Query("SELECT * FROM ${AppDbContract.PlaceEntity.TABLE_NAME} ")
    fun getAll(): List<PlaceDBO>

    @Update
    fun update(place: PlaceDBO)
}