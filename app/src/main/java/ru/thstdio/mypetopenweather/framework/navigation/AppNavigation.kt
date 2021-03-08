package ru.thstdio.mypetopenweather.framework.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.thstdio.core.navigation.AppRouter
import ru.thstdio.core.navigation.Features
import ru.thstdio.feature_cities.api.CitiesFeatureApi
import ru.thstdio.mypetopenweather.R
import ru.thstdio.mypetopenweather.presentation.detail.DetailScreen
import ru.thstdio.mypetopenweather.presentation.map.MapScreen
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class AppNavigation @Inject constructor(
    private val router: Router,
    private val navigatorHolder: NavigatorHolder,
    private val featureCities: Provider<CitiesFeatureApi>
) : AppRouter<Router> {
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
        when (feature) {
            Features.Cities -> featureCities.get().citiesStarter().start(this)
            is Features.Detail -> router.navigateTo(DetailScreen(feature.place))
            is Features.Map -> router.navigateTo(MapScreen(feature.place))
        }
    }

    override fun getRouter(): Router {
        return router
    }

    override fun onBack() {
        router.exit()
    }

}