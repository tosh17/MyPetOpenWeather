package ru.thstdio.core_db.impl.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.thstdio.core_db.impl.data.entity.PlaceDBO

@Dao
interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: PlaceDBO): Long

    @Query("SELECT * FROM place ")
    fun getAllAsFlow(): Flow<List<PlaceDBO>>

    @Query("SELECT * FROM place")
    fun getAll(): List<PlaceDBO>

    @Update
    fun update(place: PlaceDBO)

    @Query("UPDATE place set last_request = :lastRequest   where  id = :cityId")
    suspend fun setPlaceToTop(cityId: Long, lastRequest: Long)
}