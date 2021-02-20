package ru.thstdio.mypetopenweather.presentation.view.util

import android.content.Context
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import ru.thstdio.mypetopenweather.R

private const val COLD_TEMPERATURE = -20
private const val COOL_TEMPERATURE = 0
private const val WARM_TEMPERATURE = 15
private const val HOT_TEMPERATURE = 30
fun calcWeatherColor(temperature: Int, context: Context): Int {
    val coldColor = ContextCompat.getColor(context, R.color.cold)
    val coolColor = ContextCompat.getColor(context, R.color.cool)
    val warmColor = ContextCompat.getColor(context, R.color.warm)
    val hotColor = ContextCompat.getColor(context, R.color.hot)
    return when {
        temperature < COLD_TEMPERATURE -> {
            coldColor
        }
        temperature < COOL_TEMPERATURE -> {
            getColor(COLD_TEMPERATURE, COOL_TEMPERATURE, temperature, coldColor, coolColor)
        }
        temperature < WARM_TEMPERATURE -> {
            getColor(COOL_TEMPERATURE, WARM_TEMPERATURE, temperature, coolColor, warmColor)
        }
        temperature < HOT_TEMPERATURE -> {
            getColor(WARM_TEMPERATURE, HOT_TEMPERATURE, temperature, warmColor, hotColor)
        }
        else -> {
            hotColor
        }
    }
}

private fun getColor(
    minTemp: Int,
    maxTemp: Int,
    currentTemp: Int,
    @ColorInt minColor: Int,
    @ColorInt maxColor: Int
): Int {
    val range =
        (currentTemp - minTemp).toFloat() / (maxTemp - minTemp)
    return ColorUtils.blendARGB(minColor, maxColor, range)
}