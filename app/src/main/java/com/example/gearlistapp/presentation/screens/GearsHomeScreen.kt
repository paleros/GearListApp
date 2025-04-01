package com.example.gearlistapp.presentation.screens


import androidx.compose.foundation.layout.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gearlistapp.presentation.viewmodel.GearViewModel

/**
 * A felszerelesek fokepernyojet reprezentalo komponens.
 * @param navController a navigacios controller
 * @param viewModel a felszerelesekhez tartozo ViewModel
 */
@Composable
fun GearsHomeScreen(viewModel: GearViewModel = viewModel(factory = GearViewModel.Factory)) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GearListScreen()
    }

}