package ru.thstdio.mypetopenweather.framework.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.thstdio.mypetopenweather.framework.bd.dao.PlaceDao
import ru.thstdio.mypetopenweather.framework.bd.entity.PlaceEntity

@Database(
    entities = [PlaceEntity::class],
    version = AppDbContract.DATABASE_VERSION, exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract fun getPlaceDao(): PlaceDao
}

