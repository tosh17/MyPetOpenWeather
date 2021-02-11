package ru.thstdio.mypetopenweather.framework.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import ru.thstdio.mypetopenweather.framework.bd.AppDb
import ru.thstdio.mypetopenweather.framework.bd.AppDbContract
import javax.inject.Singleton

@Module
class BdModule {
    companion object {
        @Singleton
        @Provides
        fun providerBd(applicationContext: Context): AppDb =
            Room.databaseBuilder(
                applicationContext,
                AppDb::class.java, AppDbContract.DATABASE_NAME
            ).setJournalMode(RoomDatabase.JournalMode.AUTOMATIC)
                //.fallbackToDestructiveMigration()
                .build()
    }
}