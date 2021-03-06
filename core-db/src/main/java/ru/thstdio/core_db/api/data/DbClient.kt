package ru.thstdio.core_db.api.data

import kotlinx.coroutines.flow.Flow
import ru.thstdio.core_db.impl.data.entity.PlaceDBO
import ru.thstdio.mypetopenweather.framework.room.entity.WeatherDBO

interface DbClient {
    val place: Place
    val weather: Weather


    interface Place {
        suspend fun insert(place: PlaceDBO): Long
        fun getAllAsFlow(): Flow<List<PlaceDBO>>
        fun getAll(): List<PlaceDBO>
        fun update(place: PlaceDBO)
        suspend fun setPlaceToTop(cityId: Long, lastRequest: Long)
        suspend fun getPlace(placeId: Long): PlaceDBO
    }

    interface Weather {
        suspend fun insert(weather: WeatherDBO): Long
        suspend fun insert(weather: List<WeatherDBO>)
        suspend fun getWeatherCity(cityId: Long): WeatherDBO?
        suspend fun getListWeatherCity(cityId: Long): List<WeatherDBO>
        suspend fun deleteOldWeather(time: Long)
    }
}