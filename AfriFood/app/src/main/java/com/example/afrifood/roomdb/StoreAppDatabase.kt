package com.example.afrifood.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StoreProductModel::class], version = 1)
abstract class StoreAppDatabase :RoomDatabase() {

    companion object{
        private var database : StoreAppDatabase? = null
        private val DATABASE_NAME = "Afrifood"

        @Synchronized
        fun getInstance(context : Context): StoreAppDatabase{
            if (database == null){
                database = Room.databaseBuilder(
                    context.applicationContext,
                    StoreAppDatabase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return database!!
        }
    }

    abstract fun storeProductDao(): StoreProductDao
}