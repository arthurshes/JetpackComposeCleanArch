package com.example.composeretrofitpaging.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [BeerEntity::class], version = 1)
abstract class MainDb:RoomDatabase() {
    abstract fun getDao():Dao
}