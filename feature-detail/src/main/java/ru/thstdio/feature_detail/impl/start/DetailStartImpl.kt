package ru.thstdio.feature_detail.impl.start

import androidx.fragment.app.Fragment
import ru.thstdio.core.domain.Place
import ru.thstdio.feature_detail.api.DetailStart
import ru.thstdio.feature_detail.impl.presentation.DetailFragment
import javax.inject.Inject

class DetailStartImpl @Inject constructor() : DetailStart {
    override fun getStartScreen(place: Place): Fragment {
        return DetailFragment.newInstance(place)
    }
}