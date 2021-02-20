package ru.thstdio.mypetopenweather.framework.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.thstdio.mypetopenweather.framework.room.dao.PlaceDao
import ru.thstdio.mypetopenweather.framework.room.entity.PlaceDBO


const val DATABASE_VERSION = 1
const val DATABASE_NAME = "app_db"

@Database(
    entities = [PlaceDBO::class],
    version = DATABASE_VERSION, exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract val placeDao: PlaceDao
}

