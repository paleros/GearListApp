package com.example.gearlistapp.presentation.screens.actualtemplate

import androidx.compose.runtime.Composable
import com.example.gearlistapp.presentation.viewmodel.CategoryViewModel
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.LocationViewModel
import com.example.gearlistapp.presentation.viewmodel.TemplateViewModel

/**
 * A főképernyőt, azaz az aktualis felszereleslistakat reprezentáló komponens.
 * @param categoryViewModel a kategoriakhoz tartozo ViewModel
 * @param gearViewModel a felszerelesekhez tartozo ViewModel
 * @param locationViewModel a helyszinekhez tartozo ViewModel
 * @param templateViewModel a sablonokhoz tartozo ViewModel
 */
@Composable
fun ActualTemplatesHomeScreen(
    categoryViewModel: CategoryViewModel,
    gearViewModel: GearViewModel,
    locationViewModel: LocationViewModel,
    templateViewModel: TemplateViewModel
) {
    ActualTemplateListScreen(
        categoryViewModel = categoryViewModel,
        gearViewModel = gearViewModel,
        locationViewModel = locationViewModel,
        templateViewModel = templateViewModel,
    )
}