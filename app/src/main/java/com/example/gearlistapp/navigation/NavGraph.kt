package com.example.gearlistapp.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gearlistapp.data.dao.CategoryDao
import com.example.gearlistapp.data.dao.GearDao
import com.example.gearlistapp.data.dao.LocationDao
import com.example.gearlistapp.data.repository.CategoryRepositoryImpl
import com.example.gearlistapp.data.repository.GearRepositoryImpl
import com.example.gearlistapp.data.repository.LocationRepositoryImpl
import com.example.gearlistapp.presentation.screens.GearsHomeScreen
import com.example.gearlistapp.presentation.screens.HomeScreen
import com.example.gearlistapp.presentation.screens.TemplatesHomeScreen
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.ui.common.BottomNavigationBar
import com.example.gearlistapp.ui.common.TopAppBar

/**
 * A navigacios grafot reprezentalo komponens.
 * @param navController a navigacios controller
 * @param modifier a modifier
 */
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    gearDao: GearDao,
    categoryDao: CategoryDao,
    locationDao: LocationDao,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    val viewModel: GearViewModel = GearViewModel(gearDao, locationDao, categoryDao)

    NavHost(navController = navController,
        startDestination = Screen.HomeScreen.route, modifier = modifier) {
        composable(Screen.HomeScreen.route) {HomeScreen(navController) }
        composable(Screen.GearsHomeScreen.route) {GearsHomeScreen(navController = navController, viewModel = viewModel)}
        composable(Screen.TemplatesHomeScreen.route) {TemplatesHomeScreen(navController) }
    }
}