package com.example.rajadav.adidas_kotlin.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [(Goal::class)], version = 2, exportSchema = false)
abstract class AppDatabase(): RoomDatabase(){

    companion object {
         private var sInstance: AppDatabase? = null
        private val DATABASE_NAME : String = "GoalList"

        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {
                synchronized(AppDatabase::class) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase::class.java, DATABASE_NAME)
                            .build()
                }
            }
            return sInstance as AppDatabase
        }
    }

    abstract fun goalDao(): GoalDao

}