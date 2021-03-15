package ru.thstdio.mypetopenweather.data


import ru.thstdio.core_db.api.data.DbClient
import javax.inject.Inject
import javax.inject.Singleton

const val COUNT_PREDICT_FOR_FIVE_DAY = 40

@Singleton
class Repository @Inject constructor(
    private val db: DbClient
) {

    suspend fun clearOldWeather() {
        val timeLimit = System.currentTimeMillis() / 1000 - hour(2)
        db.weather.deleteOldWeather(timeLimit)
    }

    private fun hour(h: Int): Int {
        return h * 60 * 60
    }
}