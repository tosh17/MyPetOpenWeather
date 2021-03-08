package ru.thstdio.core.navigation

import ru.thstdio.core.domain.Place

sealed class Features {
    object Cities : Features()
    data class Detail(val place: Place) : Features()
    data class Map(val place: Place) : Features()
}
