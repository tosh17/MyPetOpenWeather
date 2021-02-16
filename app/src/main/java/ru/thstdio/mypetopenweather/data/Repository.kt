package ru.thstdio.mypetopenweather.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.thstdio.mypetopenweather.data.convertor.toPlace
import ru.thstdio.mypetopenweather.data.convertor.toWeather
import ru.thstdio.mypetopenweather.domain.Location
import ru.thstdio.mypetopenweather.domain.Place
import ru.thstdio.mypetopenweather.domain.PlaceWeather
import ru.thstdio.mypetopenweather.domain.Weather
import ru.thstdio.mypetopenweather.framework.api.service.OpenWeatherApi
import ru.thstdio.mypetopenweather.framework.room.AppDb
import ru.thstdio.mypetopenweather.framework.room.entity.PlaceDBO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val api: OpenWeatherApi,
    private val db: AppDb
) {

    //todo или лучше через Atomic
    private val weatherCache: HashMap<Place, Weather> = hashMapOf()
    private val mutex = Mutex()

    suspend fun getWeatherPredictForCity(city: String): Pair<Place, Weather> {
        val response = api.getWeatherByCityName(city = city)
        val placeDBO = response.toPlace()
        db.placeDao.insert(placeDBO)
        val place = placeDBO.toPlace()
        val weather = response.toWeather()
        mutex.withLock { weatherCache[place] = weather }
        return place to weather
    }

    fun getAllPlaced(): Flow<List<Place>> = db.placeDao.getAllAsFlow()
        .map { places ->
            places.sortedByDescending(PlaceDBO::lastRequest)
                .map { entity -> entity.toPlace() }
        }


    suspend fun getWeatherPredictForPlace(place: Place): Weather {
        mutex.withLock {
            if (weatherCache.containsKey(place)) return weatherCache[place]!!
        }
        val response = api.getWeatherByCityId(cityId = place.cityId)
        val weather = response.toWeather()
        mutex.withLock { weatherCache[place] = weather }
        return weather
    }

    suspend fun getWeatherPredictForCoordinate(location: Location): Pair<Place, Weather> {
        val response = api.getWeatherByCoordinates(location.latitude, location.longitude)
        val weather = response.toWeather()
        val placeDBO = response.toPlace()
        db.placeDao.insert(placeDBO)
        val place = placeDBO.toPlace()
        mutex.withLock { weatherCache[place] = weather }
        return place to weather
    }

    suspend fun getAllPlacedWithLoadedWeather(): Flow<List<PlaceWeather>> {
        return db.placeDao.getAllAsFlow()
            .map { places ->
                places.sortedByDescending(PlaceDBO::lastRequest)
                    .map { entity -> entity.toPlace() }
                    .map { place -> place to getWeatherPredictForPlace(place) }
            }
    }
}