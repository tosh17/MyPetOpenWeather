package ru.thstdio.feature_detail.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.thstdio.core.di.PerFeature
import javax.inject.Inject

@PerFeature
class DetailViewModelFactory @Inject constructor(
    private val useCase: DetailUserCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            DetailViewModel::class.java -> DetailViewModel(useCase)
            else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
        } as T

    }

}