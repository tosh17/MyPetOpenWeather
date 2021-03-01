package ru.thstdio.mypetopenweather.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.thstdio.mypetopenweather.data.convertor.*
import ru.thstdio.mypetopenweather.domain.*
import ru.thstdio.mypetopenweather.framework.api.response.WeatherPredictResponse
import ru.thstdio.mypetopenweather.framework.api.service.OpenWeatherApi
import ru.thstdio.mypetopenweather.framework.room.AppDb
import ru.thstdio.mypetopenweather.framework.room.entity.PlaceDBO
import javax.inject.Inject
import javax.inject.Singleton

const val COUNT_PREDICT_FOR_FIVE_DAY = 40

@Singleton
class Repository @Inject constructor(
    private val api: OpenWeatherApi,
    private val db: AppDb
) {

    suspend fun clearOldWeather() {
        val timeLimit = System.currentTimeMillis() / 1000 - hour(2)
        db.weatherDao.deleteOldWeather(timeLimit)
    }

    suspend fun getWeatherPredictForCity(city: String): PlaceAndWeather {
        val response = api.getWeatherByCityName(city = city)
        return getWeatherFromResponse(response)
    }

    fun getAllPlaced(): Flow<List<Place>> = db.placeDao.getAllAsFlow()
        .map { places ->
            places.sortedByDescending(PlaceDBO::lastRequest)
                .map { entity -> entity.toPlace() }
        }

    suspend fun getWeatherPredictForPlace(place: Place): Weather {
        var weatherDBO = db.weatherDao.getWeatherCity(place.cityId)
        if (weatherDBO != null) return weatherDBO.toWeather()
        val response = api.getWeatherByCityId(cityId = place.cityId)
        weatherDBO = response.toWeatherDBO(isHourly = false)
        db.weatherDao.insert(weatherDBO)
        return weatherDBO.toWeather()
    }

    suspend fun getWeatherPredictForCoordinate(location: Location): PlaceAndWeather {
        val response = api.getWeatherByCoordinates(location.latitude, location.longitude)
        return getWeatherFromResponse(response)
    }

    suspend fun getAllPlacedWithLoadedWeather(): Flow<List<PlaceAndWeather>> {
        return db.placeDao.getAllAsFlow()
            .map { places ->
                places.sortedByDescending(PlaceDBO::lastRequest)
                    .map { entity -> entity.toPlace() }
                    .map { place ->
                        PlaceAndWeather(
                            place = place,
                            weather = getWeatherPredictForPlace(place)
                        )
                    }
            }
    }

    suspend fun getWeatherPredictFiveDay(placeId: Long): Flow<PredictForFiveDay> = flow {
        var listWeatherDBO = db.weatherDao.getListWeatherCity(placeId)

        val getFromApi: suspend () -> Unit = {
            val response = api.getWeather5DayByCityId(placeId)
            val place = response.toPlace()
            listWeatherDBO = response.weathers
                .map { item -> item.toWeatherDBO(place.cityId, isHourly = true) }
                .sortedBy { item -> item.time }
            db.weatherDao.insert(listWeatherDBO)
            val listWeather = listWeatherDBO.map { item -> item.toWeatherWithDate() }
            emit(PredictForFiveDay(place, listWeather))
        }
        val getFromDb: suspend () -> Unit = {
            val place = db.placeDao.getPlace(placeId).toPlace()
            val listWeather = listWeatherDBO.map { item -> item.toWeatherWithDate() }
            emit(PredictForFiveDay(place, listWeather))
        }
        when {
            listWeatherDBO.isEmpty() -> {
                getFromApi()
            }
            listWeatherDBO.size < COUNT_PREDICT_FOR_FIVE_DAY -> {
                getFromDb()
                getFromApi()
            }
            else -> {
                getFromDb()
            }
        }
    }

    suspend fun setPlaceToTop(cityId: Long) {
        db.placeDao.setPlaceToTop(cityId, System.currentTimeMillis())
    }


    private suspend fun getWeatherFromResponse(response: WeatherPredictResponse): PlaceAndWeather {
        val placeDBO = response.toPlaceDBO()
        db.placeDao.insert(placeDBO)
        val weatherDBO = response.toWeatherDBO(false)
        db.weatherDao.insert(weatherDBO)
        val place = placeDBO.toPlace()
        val weather = weatherDBO.toWeather()
        return PlaceAndWeather(place, weather)
    }

    private fun hour(h: Int): Int {
        return h * 60 * 60
    }
}