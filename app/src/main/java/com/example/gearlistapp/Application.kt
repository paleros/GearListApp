package com.example.gearlistapp

import android.app.Application
import androidx.room.Room
import com.example.gearlistapp.data.database.AppDatabase
import com.example.gearlistapp.data.repository.CategoryRepositoryImpl
import com.example.gearlistapp.data.repository.LocationRepositoryImpl
import com.example.gearlistapp.data.repository.GearRepositoryImpl

/**
 * Az alkalmazás fo osztalya,
 * amely az alkalmazas indulasakor hozza letre az adatbazist es a repository-kat.
 */
class GearApplication : Application() {

    companion object {
        private lateinit var db: AppDatabase

        lateinit var gearRepository: GearRepositoryImpl
        lateinit var categoryRepository: CategoryRepositoryImpl
        lateinit var locationRepository: LocationRepositoryImpl
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "gearlist_database"
        ).fallbackToDestructiveMigration().build()

        gearRepository = GearRepositoryImpl(db.gearDao())
        categoryRepository = CategoryRepositoryImpl(db.categoryDao())
        locationRepository = LocationRepositoryImpl(db.locationDao())
    }
}