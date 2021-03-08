package ru.thstdio.feature_cities.impl.data

import com.example.core.di.general.PerFeature
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.thstdio.core.domain.Location
import ru.thstdio.core.domain.Place
import ru.thstdio.core.domain.PlaceAndWeather
import ru.thstdio.core.domain.Weather
import ru.thstdio.core_db.api.data.DbClient
import ru.thstdio.core_db.impl.data.entity.PlaceDBO
import ru.thstdio.feature_cities.impl.data.convertor.toPlace
import ru.thstdio.feature_cities.impl.data.convertor.toPlaceDBO
import ru.thstdio.feature_cities.impl.data.convertor.toWeather
import ru.thstdio.feature_cities.impl.data.convertor.toWeatherDBO
import ru.thstdio.feature_cities.impl.framework.api.OpenWeatherApi
import ru.thstdio.feature_cities.impl.framework.api.response.WeatherPredictResponse
import javax.inject.Inject

@PerFeature
class Repository @Inject constructor(
    private val api: OpenWeatherApi,
    private val db: DbClient
) {
    fun getAllPlaced(): Flow<List<Place>> {
        return db.place.getAllAsFlow()
            .map { places ->
                places.sortedByDescending(PlaceDBO::lastRequest)
                    .map { entity -> entity.toPlace() }
            }
    }

    suspend fun getWeatherPredictForCity(city: String): PlaceAndWeather {
        val response = api.getWeatherByCityName(city = city)
        return getWeatherFromResponse(response)
    }

    suspend fun getWeatherPredictForCoordinate(location: Location): PlaceAndWeather {
        val response = api.getWeatherByCoordinates(location.latitude, location.longitude)
        return getWeatherFromResponse(response)
    }

    suspend fun getWeatherPredictForPlace(place: Place): Weather {
        var weatherDBO = db.weather.getWeatherCity(place.cityId)
        if (weatherDBO != null) return weatherDBO.toWeather()
        val response = api.getWeatherByCityId(cityId = place.cityId)
        weatherDBO = response.toWeatherDBO(isHourly = false)
        db.weather.insert(weatherDBO)
        return weatherDBO.toWeather()
    }

    suspend fun setPlaceToTop(cityId: Long) {
        db.place.setPlaceToTop(cityId, System.currentTimeMillis())
    }

    private suspend fun getWeatherFromResponse(response: WeatherPredictResponse): PlaceAndWeather {
        val placeDBO = response.toPlaceDBO()
        db.place.insert(placeDBO)
        val weatherDBO = response.toWeatherDBO(false)
        db.weather.insert(weatherDBO)
        val place = placeDBO.toPlace()
        val weather = weatherDBO.toWeather()
        return PlaceAndWeather(place, weather)
    }
}