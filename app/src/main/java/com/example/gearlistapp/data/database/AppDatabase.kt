package com.example.gearlistapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gearlistapp.data.dao.CategoryDao
import com.example.gearlistapp.data.dao.GearDao
import com.example.gearlistapp.data.dao.LocationDao
import com.example.gearlistapp.data.entities.CategoryEntity
import com.example.gearlistapp.data.entities.GearEntity
import com.example.gearlistapp.data.entities.LocationEntity

/**
 * Az alkalmazas adatbazis osztalya.
 */
@Database(entities = [GearEntity::class, CategoryEntity::class, LocationEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gearDao(): GearDao
    abstract fun categoryDao(): CategoryDao
    abstract fun locationDao(): LocationDao
}