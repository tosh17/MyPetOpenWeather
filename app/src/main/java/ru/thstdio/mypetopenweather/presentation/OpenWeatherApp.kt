package ru.thstdio.mypetopenweather.presentation

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import ru.thstdio.mypetopenweather.framework.di.component.AppComponent
import ru.thstdio.mypetopenweather.framework.di.component.DaggerAppComponent

class OpenWeatherApp : Application() {
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder: NavigatorHolder
        get() = cicerone.getNavigatorHolder()
    lateinit var daggerComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        daggerComponent = DaggerAppComponent.builder().setContext(applicationContext).build()
    }
}