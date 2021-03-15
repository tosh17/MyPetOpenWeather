package ru.thstdio.feature_map.impl.start

import androidx.fragment.app.Fragment
import ru.thstdio.core.domain.Place
import ru.thstdio.feature_map.api.MapStarter
import ru.thstdio.feature_map.impl.presentation.MapFragment
import javax.inject.Inject

class MapStarterImpl @Inject constructor() : MapStarter {
    override fun getStartScreen(place: Place): Fragment {
        return MapFragment.newInstance(place)
    }
}