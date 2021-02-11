package ru.thstdio.mypetopenweather.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.thstdio.mypetopenweather.R
import ru.thstdio.mypetopenweather.presentation.city.CityScreen

class OpenWeatherActivity : AppCompatActivity() {
    private val openWeatherApp: OpenWeatherApp get() = application as OpenWeatherApp
    private val navigatorHolder: NavigatorHolder get() = openWeatherApp.navigatorHolder
    private val router: Router get() = openWeatherApp.router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_weather)
        if (savedInstanceState == null) {
            router.navigateTo(CityScreen())
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(AppNavigator(this, R.id.fragment_container_view))
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}