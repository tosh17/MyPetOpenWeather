package ru.thstdio.mypetopenweather.presentation.view.util

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import ru.thstdio.mypetopenweather.R

private const val MIN_TEMPERATURE = -20
private const val MAX_TEMPERATURE = 30
fun calcWeatherColor(temperature: Double, context: Context): Int {
    val startColor = ContextCompat.getColor(context, R.color.cold)
    val endColor = ContextCompat.getColor(context, R.color.hot)
    val range = ((temperature - MIN_TEMPERATURE) / (MAX_TEMPERATURE - MIN_TEMPERATURE)).toFloat()

    return ColorUtils.blendARGB(
        startColor, endColor,
        when {
            range < 0f -> 0f
            range > 1f -> 1f
            else -> range
        }
    )
}