package ru.thstdio.feature_map.impl.data


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.thstdio.core.domain.Place
import ru.thstdio.core.domain.PlaceAndWeather
import ru.thstdio.core.domain.Weather
import ru.thstdio.core_db.api.data.DbClient
import ru.thstdio.core_db.impl.data.entity.PlaceDBO
import ru.thstdio.feature_map.impl.framework.api.OpenWeatherApi
import ru.thstdio.module_injector.di.PerFeature
import javax.inject.Inject


@PerFeature
class Repository @Inject constructor(
    private val api: OpenWeatherApi,
    private val db: DbClient
) {


    private suspend fun getWeatherPredictForPlace(place: Place): Weather {
        var weatherDBO = db.weather.getWeatherCity(place.cityId)
        if (weatherDBO != null) return weatherDBO.toWeather()
        val response = api.getWeatherByCityId(cityId = place.cityId)
        weatherDBO = response.toWeatherDBO(isHourly = false)
        db.weather.insert(weatherDBO)
        return weatherDBO.toWeather()
    }


    suspend fun getAllPlacedWithLoadedWeather(): Flow<List<PlaceAndWeather>> {
        return db.place.getAllAsFlow()
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

}