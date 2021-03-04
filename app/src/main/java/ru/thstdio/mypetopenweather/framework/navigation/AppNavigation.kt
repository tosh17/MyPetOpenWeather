package ru.thstdio.mypetopenweather.framework.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.thstdio.mypetopenweather.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigation @Inject constructor(
    val router: Router,
    private val navigatorHolder: NavigatorHolder
) {
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


}