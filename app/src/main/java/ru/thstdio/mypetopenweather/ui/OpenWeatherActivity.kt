package ru.thstdio.mypetopenweather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.thstdio.mypetopenweather.R

class OpenWeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_weather)
    }
}