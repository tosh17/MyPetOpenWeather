package ru.thstdio.feature_cities.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.thstdio.core.di.PerFeature
import ru.thstdio.core.navigation.AppRouter
import javax.inject.Inject

@PerFeature
class CityViewModelFactory @Inject constructor(
    private val useCase: CityUseCase,
    private val navigation: AppRouter
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            CityViewModel::class.java -> CityViewModel(useCase, navigation)
            else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
        } as T

    }

}