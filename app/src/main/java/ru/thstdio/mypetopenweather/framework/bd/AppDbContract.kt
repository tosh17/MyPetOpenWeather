package ru.thstdio.mypetopenweather.framework.bd

object AppDbContract {
    const val DATABASE_NAME = "app_db.db"
    const val DATABASE_VERSION = 1

    object PlaceEntity {
        const val TABLE_NAME = "place"

        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_LATITUDE= "latitude"
        const val COLUMN_NAME_LONGITUDE = "longitude"
        const val COLUMN_NAME_LAST_REQUEST = "last_request"
    }
}
