package com.example.calendartest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DeedItem::class], version = 1)
abstract class DeedDatabase : RoomDatabase() {
    abstract fun deedDao(): DeedDatabaseDao
    companion object {
        private var INSTANCE: DeedDatabase? = null
        fun getInstance(context: Context): DeedDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DeedDatabase::class.java,
                        "deed_list_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}