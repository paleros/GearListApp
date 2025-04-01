package com.example.gearlistapp.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gearlistapp.presentation.screens.GearsHomeScreen
import com.example.gearlistapp.presentation.screens.HomeScreen
import com.example.gearlistapp.presentation.screens.TemplatesHomeScreen

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

    NavHost(navController = navController,
        startDestination = Screen.HomeScreen.route,
        modifier = modifier) {
        composable(Screen.HomeScreen.route) {HomeScreen() }
        composable(Screen.GearsHomeScreen.route) {GearsHomeScreen() }
        composable(Screen.TemplatesHomeScreen.route) {TemplatesHomeScreen() }
    }
}