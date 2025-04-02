package com.example.gearlistapp.presentation.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gearlistapp.presentation.viewmodel.CategoryViewModel
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.LocationViewModel

/**
 * A sablonok fokepernyojet reprezentalo komponens.
 * @param categoryViewModel a kategoriakhoz tartozo ViewModel
 * @param gearViewModel a felszerelesekhez tartozo ViewModel
 * @param locationViewModel a helyszinekhez tartozo ViewModel
 */
@Composable
fun TemplatesHomeScreen(
    categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.Factory),
    gearViewModel: GearViewModel = viewModel(factory = GearViewModel.Factory),
    locationViewModel: LocationViewModel = viewModel(factory = LocationViewModel.Factory),
) {
    Text("This is Templates Home Screen")
}

/**
 * Preview fuggveny a TemplatesHomeScreen-hoz.
 */
@Preview(showBackground = true)
@Composable
fun PreviewTemplatesHomeScreen() {
    TemplatesHomeScreen()
}