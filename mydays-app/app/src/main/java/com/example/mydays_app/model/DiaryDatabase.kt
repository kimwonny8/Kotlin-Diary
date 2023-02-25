package com.example.mydays_app.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DiaryRecord::class], version=1)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao

    companion object{
        private var INSTANCE: DiaryDatabase?=null
        @JvmStatic
        fun getInstance(context: Context): DiaryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DiaryDatabase::class.java,
                    "my.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}