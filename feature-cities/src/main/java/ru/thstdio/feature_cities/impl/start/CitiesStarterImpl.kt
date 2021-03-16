package ru.thstdio.feature_cities.impl.start

import androidx.fragment.app.Fragment
import ru.thstdio.feature_cities.api.CitiesStarter
import ru.thstdio.feature_cities.impl.presentation.CityFragment
import ru.thstdio.module_injector.di.PerFeature
import javax.inject.Inject

@PerFeature
class CitiesStarterImpl @Inject constructor() : CitiesStarter {
    override fun getStartScreen(): Fragment {
        return CityFragment.newInstance()
    }
}