package ru.thstdio.core_db.impl.data

import kotlinx.coroutines.flow.Flow
import ru.thstdio.core_db.api.data.DbClient
import ru.thstdio.core_db.impl.data.entity.PlaceDBO
import ru.thstdio.mypetopenweather.framework.room.AppDb
import ru.thstdio.mypetopenweather.framework.room.entity.WeatherDBO
import javax.inject.Inject

class DbClientImpl @Inject constructor(private val db: AppDb) : DbClient {
    private val placeImpl = object : DbClient.Place {
        override suspend fun insert(place: PlaceDBO): Long {
            return db.placeDao.insert(place)
        }

        override fun getAllAsFlow(): Flow<List<PlaceDBO>> {
            return db.placeDao.getAllAsFlow()
        }

        override fun getAll(): List<PlaceDBO> {
            return db.placeDao.getAll()
        }

        override fun update(place: PlaceDBO) {
            return db.placeDao.update(place)
        }

        override suspend fun setPlaceToTop(cityId: Long, lastRequest: Long) {
            return db.placeDao.setPlaceToTop(cityId, lastRequest)
        }

        override suspend fun getPlace(placeId: Long): PlaceDBO {
            return db.placeDao.getPlace(placeId)
        }
    }
    private val weatherImpl = object : DbClient.Weather {
        override suspend fun insert(weather: WeatherDBO): Long {
            return db.weatherDao.insert(weather)
        }

        override suspend fun insert(weather: List<WeatherDBO>) {
            return db.weatherDao.insert(weather)
        }

        override suspend fun getWeatherCity(cityId: Long): WeatherDBO? {
            return db.weatherDao.getWeatherCity(cityId)
        }

        override suspend fun getListWeatherCity(cityId: Long): List<WeatherDBO> {
            return db.weatherDao.getListWeatherCity(cityId)
        }

        override suspend fun deleteOldWeather(time: Long) {
            return db.weatherDao.deleteOldWeather(time)
        }

    }

    override val place: DbClient.Place
        get() = placeImpl
    override val weather: DbClient.Weather
        get() = weatherImpl
}