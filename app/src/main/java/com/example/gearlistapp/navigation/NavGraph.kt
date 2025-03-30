package com.example.gearlistapp.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gearlistapp.GearApplication.Companion.gearRepository
import com.example.gearlistapp.data.dao.CategoryDao
import com.example.gearlistapp.data.dao.GearDao
import com.example.gearlistapp.data.dao.LocationDao
import com.example.gearlistapp.domain.usecases.gear.GearUseCases
import com.example.gearlistapp.presentation.screens.GearsHomeScreen
import com.example.gearlistapp.presentation.screens.HomeScreen
import com.example.gearlistapp.presentation.screens.TemplatesHomeScreen
import com.example.gearlistapp.presentation.viewmodel.GearViewModel

/**
 * A navigacios grafot reprezentalo komponens.
 * @param navController a navigacios controller
 * @param modifier a modifier
 */
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    //val gearOperations = GearUseCases(gearRepository)

    NavHost(navController = navController,
        startDestination = Screen.HomeScreen.route,
        modifier = modifier) {
        composable(Screen.HomeScreen.route) {HomeScreen() }
        composable(Screen.GearsHomeScreen.route) {GearsHomeScreen() }
        composable(Screen.TemplatesHomeScreen.route) {TemplatesHomeScreen() }
    }
}