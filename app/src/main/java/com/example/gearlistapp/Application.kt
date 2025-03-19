package com.example.gearlistapp

import android.app.Application
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import androidx.room.Room
import com.example.gearlistapp.navigation.Screen
import com.example.gearlistapp.presentation.screens.HomeScreen

class GearApplication : Application() {

    /*companion object {
        private lateinit var db: GearDatabase

        lateinit var repository: GearRepositoryImpl
    }*/

    override fun onCreate() {
        super.onCreate()
        /*db = Room.databaseBuilder(
            applicationContext,
            GearDatabase::class.java,
            "gear_database"
        ).fallbackToDestructiveMigration().build()

        repository = GearRepositoryImpl(db.dao)*/
    }
}