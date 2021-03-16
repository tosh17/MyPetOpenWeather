package ru.thstdio.feature_map.api

import androidx.fragment.app.Fragment
import ru.thstdio.core.domain.Place

interface MapStarter {
    fun getStartScreen(place: Place): Fragment
}