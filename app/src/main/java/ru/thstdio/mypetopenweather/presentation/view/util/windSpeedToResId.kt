package ru.thstdio.mypetopenweather.presentation.view.util

import ru.thstdio.mypetopenweather.R

fun winSpeedToResId(speed: Double): Int {
    return when {
        speed < 0.3 -> R.string.wind_speed_0
        speed < 1.6 -> R.string.wind_speed_0_3
        speed < 3.4 -> R.string.wind_speed_1_6
        speed < 5.5 -> R.string.wind_speed_3_4
        speed < 8.0 -> R.string.wind_speed_5_5
        speed < 10.8 -> R.string.wind_speed_8_0
        speed < 13.9 -> R.string.wind_speed_10_8
        speed < 17.2 -> R.string.wind_speed_13_9
        speed < 20.8 -> R.string.wind_speed_17_2
        speed < 24.5 -> R.string.wind_speed_20_8
        speed < 28.5 -> R.string.wind_speed_24_5
        speed < 33 -> R.string.wind_speed_28_5
        else -> R.string.wind_speed_33


    }
}