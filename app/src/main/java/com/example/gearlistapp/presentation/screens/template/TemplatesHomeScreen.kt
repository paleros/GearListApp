package com.example.gearlistapp.presentation.screens.template

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.gearlistapp.presentation.viewmodel.CategoryViewModel
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.LocationViewModel
import com.example.gearlistapp.presentation.viewmodel.TemplateViewModel

/**
 * A sablonok fokepernyojet reprezentalo komponens.
 * @param categoryViewModel a kategoriakhoz tartozo ViewModel
 * @param gearViewModel a felszerelesekhez tartozo ViewModel
 * @param locationViewModel a helyszinekhez tartozo ViewModel
 * @param templateViewModel a sablonokhoz tartozo ViewModel
 */
@Composable
fun TemplatesHomeScreen(
    categoryViewModel: CategoryViewModel,
    gearViewModel: GearViewModel,
    locationViewModel: LocationViewModel,
    templateViewModel: TemplateViewModel,
    navController: NavHostController
) {
    TemplateListScreen(
        categoryViewModel = categoryViewModel,
        gearViewModel = gearViewModel,
        locationViewModel = locationViewModel,
        templateViewModel = templateViewModel,
        navController = navController,
    )
}
