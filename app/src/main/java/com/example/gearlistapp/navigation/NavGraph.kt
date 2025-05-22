package com.example.gearlistapp.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gearlistapp.presentation.screens.gear.GearsHomeScreen
import com.example.gearlistapp.presentation.screens.actualtemplate.ActualTemplatesHomeScreen
import com.example.gearlistapp.presentation.screens.template.TemplatesHomeScreen
import com.example.gearlistapp.presentation.viewmodel.CategoryViewModel
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.LocationViewModel
import com.example.gearlistapp.presentation.viewmodel.TemplateViewModel

/**
 * A navigacios grafot reprezentalo komponens.
 * @param gearViewModel a felszerelesekhez tartozo ViewModel
 * @param categoryViewModel a kategoriakhoz tartozo ViewModel
 * @param locationViewModel a helyszinekhez tartozo ViewModel
 * @param templateViewModel a sablonokhoz tartozo ViewModel
 * @param navController a navigacios controller
 * @param modifier a modifier
 */
@Composable
fun NavGraph(
    gearViewModel: GearViewModel,
    categoryViewModel: CategoryViewModel,
    locationViewModel: LocationViewModel,
    templateViewModel: TemplateViewModel,
    navController: NavHostController = rememberNavController(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {

    NavHost(navController = navController,
        startDestination = Screen.HomeScreen.route,
        modifier = modifier) {
        composable(Screen.HomeScreen.route) {ActualTemplatesHomeScreen(
                                                            categoryViewModel = categoryViewModel,
                                                            gearViewModel = gearViewModel,
                                                            locationViewModel = locationViewModel,
                                                            templateViewModel = templateViewModel,
        ) }
        composable(Screen.GearsHomeScreen.route) {GearsHomeScreen(
                                                            categoryViewModel = categoryViewModel,
                                                            gearViewModel = gearViewModel,
                                                            locationViewModel = locationViewModel,) }
        composable(Screen.TemplatesHomeScreen.route) {TemplatesHomeScreen(
                                                            categoryViewModel = categoryViewModel,
                                                            gearViewModel = gearViewModel,
                                                            locationViewModel = locationViewModel,
                                                            navController = navController,
                                                            templateViewModel = templateViewModel,
        ) }
    }
}