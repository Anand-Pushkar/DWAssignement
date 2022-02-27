package com.learningcurve.digitalwolfeassignement.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.learningcurve.digitalwolfeassignement.cache.entities.MyEntity

@Database(
    entities = [
        MyEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun myDao(): MyDao

    companion object {
        val DATABASE_NAME = "app_db"
    }
}