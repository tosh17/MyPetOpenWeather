package ru.thstdio.core_db.api.di

import ru.thstdio.core_db.api.data.DbClient

interface CoreDbApi {
    fun dbClient(): DbClient
}