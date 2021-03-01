package ru.thstdio.mypetopenweather.framework.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.thstdio.mypetopenweather.framework.room.entity.WeatherDBO

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: WeatherDBO): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: List<WeatherDBO>)

    @Query("SELECT * FROM weather Where city_id= :cityId Order by time ")
    suspend fun getWeatherCity(cityId: Long): WeatherDBO?

    @Query("SELECT * FROM weather Where city_id= :cityId and is_hourly=1 Order by time ")
    suspend fun getListWeatherCity(cityId: Long): List<WeatherDBO>


    @Query("Delete FROM    weather    where  time < :time")
    suspend fun deleteOldWeather(time: Long)

}