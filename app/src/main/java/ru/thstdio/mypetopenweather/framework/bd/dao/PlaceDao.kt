package ru.thstdio.mypetopenweather.framework.bd.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.thstdio.mypetopenweather.framework.bd.AppDbContract
import ru.thstdio.mypetopenweather.framework.bd.entity.PlaceEntity

@Dao
interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: PlaceEntity): Long

    @Query("SELECT * FROM ${AppDbContract.PlaceEntity.TABLE_NAME} ")
    fun getAll(): Flow<List<PlaceEntity>>

    @Update
    fun update(place: PlaceEntity)
}