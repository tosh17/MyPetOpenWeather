package ru.thstdio.mypetopenweather.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.thstdio.core.navigation.Features
import ru.thstdio.mypetopenweather.R
import ru.thstdio.mypetopenweather.framework.navigation.AppNavigation
import ru.thstdio.mypetopenweather.usecase.repository.ClearOldWeather
import javax.inject.Inject

@AndroidEntryPoint
class OpenWeatherActivity : AppCompatActivity() {
    @Inject
    lateinit var appNavigator: AppNavigation

    @Inject
    lateinit var clearOldWeatherUseCase: ClearOldWeather
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_weather)
        if (savedInstanceState == null) {
            appNavigator.openFeature(Features.Cities)
            CoroutineScope(SupervisorJob()).launch {
                clearOldWeatherUseCase()
            }
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
        appNavigator.onBack()
    }
}