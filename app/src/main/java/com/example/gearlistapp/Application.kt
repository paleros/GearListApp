package com.example.gearlistapp

import android.app.Application
import androidx.room.Room
import com.example.gearlistapp.data.database.AppDatabase
import com.example.gearlistapp.data.repository.CategoryRepository
import com.example.gearlistapp.data.repository.LocationRepository
import com.example.gearlistapp.data.repository.GearRepository
import com.example.gearlistapp.data.repository.TemplateRepository

/**
 * Az alkalmazás fo osztalya,
 * amely az alkalmazas indulasakor hozza letre az adatbazist es a repository-kat.
 */
class GearApplication : Application() {

    companion object {
        private lateinit var db: AppDatabase

        lateinit var gearRepository: GearRepository
        lateinit var categoryRepository: CategoryRepository
        lateinit var locationRepository: LocationRepository
        lateinit var templateRepository: TemplateRepository
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "gearlist_database"
        ).build()

        gearRepository = GearRepository(db.gearDao)
        categoryRepository = CategoryRepository(db.categoryDao)
        locationRepository = LocationRepository(db.locationDao)
        templateRepository = TemplateRepository(db.templateDao)
    }
}