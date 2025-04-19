package com.example.gearlistapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gearlistapp.data.Converters
import com.example.gearlistapp.data.dao.CategoryDao
import com.example.gearlistapp.data.dao.GearDao
import com.example.gearlistapp.data.dao.LocationDao
import com.example.gearlistapp.data.dao.TemplateDao
import com.example.gearlistapp.data.entities.CategoryEntity
import com.example.gearlistapp.data.entities.GearEntity
import com.example.gearlistapp.data.entities.LocationEntity
import com.example.gearlistapp.data.entities.TemplateEntity

/**
 * Az alkalmazas adatbazis osztalya.
 */
@Database(entities = [GearEntity::class, CategoryEntity::class, LocationEntity::class, TemplateEntity::class],
    version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val gearDao : GearDao
    abstract val categoryDao : CategoryDao
    abstract val locationDao : LocationDao
    abstract val templateDao : TemplateDao
}