package ru.thstdio.core_network.api.di

import retrofit2.Retrofit

interface CoreNetwork {
    fun getHttpClient(): Retrofit
}