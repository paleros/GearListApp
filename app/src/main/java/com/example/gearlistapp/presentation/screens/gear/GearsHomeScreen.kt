package com.example.gearlistapp.presentation.screens.gear

import androidx.compose.runtime.Composable
import com.example.gearlistapp.presentation.viewmodel.CategoryViewModel
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.LocationViewModel

/**
 * A felszerelesek fokepernyojet reprezentalo komponens.
 * @param categoryViewModel a kategoriakhoz tartozo ViewModel
 * @param gearViewModel a felszerelesekhez tartozo ViewModel
 * @param locationViewModel a helyszinekhez tartozo ViewModel
 */
@Composable
fun GearsHomeScreen(
    categoryViewModel: CategoryViewModel,
    gearViewModel: GearViewModel,
    locationViewModel: LocationViewModel,
) {

    GearListScreen(
        categoryViewModel = categoryViewModel,
        gearViewModel = gearViewModel,
        locationViewModel = locationViewModel,)

}