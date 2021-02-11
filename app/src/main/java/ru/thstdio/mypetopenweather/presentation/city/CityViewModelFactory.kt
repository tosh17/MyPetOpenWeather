package ru.thstdio.mypetopenweather.presentation.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CityViewModelFactory(private val useCase: CityUseCase) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        CityViewModel::class.java -> CityViewModel(useCase)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}