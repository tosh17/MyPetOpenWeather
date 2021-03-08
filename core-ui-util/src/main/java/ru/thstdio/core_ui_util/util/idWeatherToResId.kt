package ru.thstdio.core_ui_util.util

import ru.thstdio.core_ui_util.R


fun convertIdWeatherToResId(id: Int): Int {
    return when {
        id / 100 == 2 -> R.drawable.ic_weather_2xx
        id / 100 == 3 -> R.drawable.ic_weather_3xx
        id / 100 == 5 -> R.drawable.ic_weather_5xx
        id / 100 == 6 -> R.drawable.ic_weather_6xx
        id / 100 == 7 -> R.drawable.ic_weather_7xx
        id == 800 -> R.drawable.ic_weather_800
        id == 801 -> R.drawable.ic_weather_801
        id == 802 -> R.drawable.ic_weather_802
        id == 803 -> R.drawable.ic_weather_803
        id == 804 -> R.drawable.ic_weather_804
        else -> R.drawable.ic_weather_na
    }
}