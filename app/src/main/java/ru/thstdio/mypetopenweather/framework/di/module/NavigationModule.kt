package ru.thstdio.mypetopenweather.framework.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.thstdio.mypetopenweather.framework.room.AppDb
import ru.thstdio.mypetopenweather.framework.room.AppDbContract
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Singleton
    @Provides
    fun providerCicerone(): Cicerone<Router> = Cicerone.create()

    @Singleton
    @Provides
    fun providerRouter(cicerone: Cicerone<Router>): Router = cicerone.router

    @Singleton
    @Provides
    fun providerNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder =
        cicerone.getNavigatorHolder()
}