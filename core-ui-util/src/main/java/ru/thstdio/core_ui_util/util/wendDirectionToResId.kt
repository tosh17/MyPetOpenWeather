package ru.thstdio.core_ui_util.util

import ru.thstdio.core_ui_util.R

fun windDirectionToResId(deg: Int): Int {
    return when {
        deg < 22 -> R.string.wind_0
        deg < 45 -> R.string.wind_22
        deg < 67 -> R.string.wind_45
        deg < 90 -> R.string.wind_67
        deg < 12 -> R.string.wind_90
        deg < 135 -> R.string.wind_112
        deg < 157 -> R.string.wind_135
        deg < 180 -> R.string.wind_157
        deg < 202 -> R.string.wind_180
        deg < 225 -> R.string.wind_202
        deg < 247 -> R.string.wind_225
        deg < 270 -> R.string.wind_247
        deg < 292 -> R.string.wind_270
        deg < 315 -> R.string.wind_292
        deg < 337 -> R.string.wind_315
        else -> R.string.wind_337

    }
}
