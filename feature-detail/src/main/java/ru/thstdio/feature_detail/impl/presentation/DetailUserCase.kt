package ru.thstdio.feature_detail.impl.presentation

import ru.thstdio.feature_detail.impl.usecase.GetWeatherPredictFiveDay
import javax.inject.Inject

class DetailUserCase @Inject constructor(val getPredict: GetWeatherPredictFiveDay)