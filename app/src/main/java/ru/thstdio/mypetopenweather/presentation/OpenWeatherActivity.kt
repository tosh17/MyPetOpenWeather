package ru.thstdio.mypetopenweather.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.thstdio.mypetopenweather.R
import ru.thstdio.mypetopenweather.framework.navigation.AppNavigation
import ru.thstdio.mypetopenweather.presentation.city.CityScreen
import ru.thstdio.mypetopenweather.presentation.map.MapScreen
import javax.inject.Inject

@AndroidEntryPoint
class OpenWeatherActivity : AppCompatActivity() {
    @Inject
    lateinit var appNavigator: AppNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_weather)
        if (savedInstanceState == null) {
            appNavigator.router.newRootScreen(CityScreen())
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        appNavigator.setNavigator(this, R.id.fragment_container_view)
    }

    override fun onPause() {
        appNavigator.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        appNavigator.router.exit()
    }
}