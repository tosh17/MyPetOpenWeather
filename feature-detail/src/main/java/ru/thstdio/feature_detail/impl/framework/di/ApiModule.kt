package ru.thstdio.feature_detail.impl.framework.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.thstdio.feature_detail.impl.framework.api.OpenWeatherApi

@Module
object ApiModule {
    @Provides
    fun provideApi(retrofit: Retrofit): OpenWeatherApi {
        return retrofit.create(OpenWeatherApi::class.java)
    }
}