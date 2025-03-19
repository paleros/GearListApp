package com.example.gearlistapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gearlistapp.data.dao.CategoryDao
import com.example.gearlistapp.data.dao.GearDao
import com.example.gearlistapp.data.dao.LocationDao
import com.example.gearlistapp.data.model.Category
import com.example.gearlistapp.data.model.Gear
import com.example.gearlistapp.data.model.Location

/**
 * Az alkalmazas adatbazis osztalya.
 */
@Database(entities = [Gear::class, Category::class, Location::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gearDao(): GearDao
    abstract fun categoryDao(): CategoryDao
    abstract fun locationDao(): LocationDao
}