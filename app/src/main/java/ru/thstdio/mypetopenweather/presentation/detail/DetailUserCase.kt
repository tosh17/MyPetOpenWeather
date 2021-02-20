package ru.thstdio.mypetopenweather.presentation.detail

import ru.thstdio.mypetopenweather.usecase.repository.GetWeatherPredictFiveDay
import javax.inject.Inject

class DetailUserCase @Inject constructor(val getPredict: GetWeatherPredictFiveDay)