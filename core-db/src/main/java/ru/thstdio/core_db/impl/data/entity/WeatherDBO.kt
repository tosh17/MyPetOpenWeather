package ru.thstdio.mypetopenweather.framework.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["city_id", "time"], tableName = "weather")
data class WeatherDBO(
    @ColumnInfo(name = "city_id") val cityId: Long,
    @ColumnInfo(name = "time") val time: Long,
    @ColumnInfo(name = "is_hourly") val isHourly: Boolean,
    @ColumnInfo(name = "temperature") val temperature: Int,
    @ColumnInfo(name = "feelsLike") val feelsLike: Int,
    @ColumnInfo(name = "iconId") val iconId: Int,
    @ColumnInfo(name = "tempMin") val tempMin: Int,
    @ColumnInfo(name = "tempMax") val tempMax: Int,
    @ColumnInfo(name = "pressure") val pressure: Int,
    @ColumnInfo(name = "humidity") val humidity: Int,
    @ColumnInfo(name = "windSpeed") val windSpeed: Double,
    @ColumnInfo(name = "wendDeg") val wendDeg: Int

)