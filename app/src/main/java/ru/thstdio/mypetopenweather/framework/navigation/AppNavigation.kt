package ru.thstdio.mypetopenweather.framework.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.thstdio.core.navigation.AppRouter
import ru.thstdio.core.navigation.Features
import ru.thstdio.feature_cities.api.CitiesFeatureApi
import ru.thstdio.feature_detail.api.DetailFeatureApi
import ru.thstdio.feature_map.api.MapFeatureApi
import ru.thstdio.mypetopenweather.R
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class AppNavigation @Inject constructor(
    private val router: Router,
    private val navigatorHolder: NavigatorHolder,
    private val featureCities: Provider<CitiesFeatureApi>,
    private val featureMap: Provider<MapFeatureApi>,
    private val featureDetail: Provider<DetailFeatureApi>
) : AppRouter {
    fun setNavigator(activity: FragmentActivity, containerId: Int) {
        navigatorHolder.setNavigator(
            object : AppNavigator(activity, containerId) {
                override fun setupFragmentTransaction(
                    fragmentTransaction: FragmentTransaction,
                    currentFragment: Fragment?,
                    nextFragment: Fragment?
                ) {
                    super.setupFragmentTransaction(
                        fragmentTransaction,
                        currentFragment,
                        nextFragment
                    )
                    fragmentTransaction.setReorderingAllowed(true)
                    fragmentTransaction.setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.exit_from_right,
                        R.anim.enter_to_left
                    )
                }
            })
    }

    fun removeNavigator() {
        navigatorHolder.removeNavigator()
    }

    override fun openFeature(feature: Features) {
        router.navigateTo(
            FragmentScreen(fragmentCreator = {
                when (feature) {
                    Features.Cities ->
                        featureCities.get().citiesStarter().getStartScreen()
                    is Features.Detail -> featureDetail.get().detailStarter()
                        .getStartScreen(feature.place)
                    is Features.Map -> featureMap.get().mapStarter().getStartScreen(feature.place)
                }
            })
        )
    }

    override fun onBack() {
        router.exit()
    }

}