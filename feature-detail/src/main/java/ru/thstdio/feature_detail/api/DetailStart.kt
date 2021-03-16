package ru.thstdio.feature_detail.api

import androidx.fragment.app.Fragment
import ru.thstdio.core.domain.Place

interface DetailStart {
    fun getStartScreen(place: Place): Fragment
}