package ru.thstdio.feature_detail.impl.data


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.thstdio.core.domain.PredictForFiveDay
import ru.thstdio.core_db.api.data.DbClient
import ru.thstdio.feature_detail.impl.framework.api.OpenWeatherApi
import ru.thstdio.module_injector.di.PerFeature
import javax.inject.Inject

const val COUNT_PREDICT_FOR_FIVE_DAY = 40

@PerFeature
class Repository @Inject constructor(
    private val api: OpenWeatherApi,
    private val db: DbClient
) {

    suspend fun getWeatherPredictFiveDay(placeId: Long): Flow<PredictForFiveDay> = flow {
        var listWeatherDBO = db.weather.getListWeatherCity(placeId)

        val getFromApi: suspend () -> Unit = {
            val response = api.getWeather5DayByCityId(placeId)
            val place = response.toPlace()
            listWeatherDBO = response.weathers
                .map { item -> item.toWeatherDBO(place.cityId, isHourly = true) }
                .sortedBy { item -> item.time }
            db.weather.insert(listWeatherDBO)
            val listWeather = listWeatherDBO.map { item -> item.toWeatherWithDate() }
            emit(PredictForFiveDay(place, listWeather))
        }
        val getFromDb: suspend () -> Unit = {
            val place = db.place.getPlace(placeId).toPlace()
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
}