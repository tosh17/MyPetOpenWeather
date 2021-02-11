package ru.thstdio.mypetopenweather.data

import kotlinx.coroutines.flow.map
import ru.thstdio.mypetopenweather.data.convertor.toPlace
import ru.thstdio.mypetopenweather.data.convertor.toWeather
import ru.thstdio.mypetopenweather.domain.Place
import ru.thstdio.mypetopenweather.domain.Weather
import ru.thstdio.mypetopenweather.framework.api.service.OpenWeatherApi
import ru.thstdio.mypetopenweather.framework.bd.AppDb
import ru.thstdio.mypetopenweather.framework.bd.entity.PlaceEntity
import javax.inject.Inject


class Repository @Inject constructor(private val api: OpenWeatherApi, private val db: AppDb) {
    suspend fun getWeatherPredictForCity(city: String): Pair<Place,Weather> {
        val response = api.getWeatherByCityName(city = city)
        val placeEntity = response.toPlace()
        db.getPlaceDao().insert(placeEntity)
        return   placeEntity.toPlace() to response.toWeather()
    }

    fun getAllPlaced() = db.getPlaceDao().getAll()
        .map { places -> places.sortedByDescending(PlaceEntity::lastRequest) }
        .map { places -> places.map { entity -> entity.toPlace() } }

    suspend fun getWeatherPredictForCityId(cityId: Long): Weather {
        val response = api.getWeatherByCityId(cityId = cityId)
        return response.toWeather()
    }


}