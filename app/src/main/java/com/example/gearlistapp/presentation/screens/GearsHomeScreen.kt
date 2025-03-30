package com.example.gearlistapp.presentation.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gearlistapp.GearApplication.Companion.gearRepository
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
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GearListScreen()
    }

}

/**
 * Preview fuggveny a GearsHomeScreen-hoz.
 */
/*@Preview(showBackground = true)
@Composable
fun PreviewGearsHomeScreen() {
    GearsHomeScreen(
        rememberNavController(),
        viewModel =
    )
}*/