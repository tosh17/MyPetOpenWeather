package ru.thstdio.mypetopenweather.framework.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.thstdio.mypetopenweather.framework.room.AppDbContract

@Entity(tableName = AppDbContract.PlaceEntity.TABLE_NAME)
data class PlaceDBO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AppDbContract.PlaceEntity.COLUMN_NAME_ID)
    val id: Long,
    @ColumnInfo(name = AppDbContract.PlaceEntity.COLUMN_NAME_NAME)
    val name: String,
    @ColumnInfo(name = AppDbContract.PlaceEntity.COLUMN_NAME_LATITUDE)
    val latitude: Double,
    @ColumnInfo(name = AppDbContract.PlaceEntity.COLUMN_NAME_LONGITUDE)
    val longitude: Double,
    @ColumnInfo(name = AppDbContract.PlaceEntity.COLUMN_NAME_LAST_REQUEST)
    val lastRequest: Long
)