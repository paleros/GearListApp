package com.example.gearlistapp.presentation.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.gearlistapp.presentation.viewmodel.GearViewModel

/**
 * A felszerelesek fokepernyojet reprezentalo komponens.
 * @param navController a navigacios controller
 * @param viewModel a felszerelesekhez tartozo ViewModel
 */
@Composable
fun GearsHomeScreen(navController: NavController, viewModel: GearViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("This is Gears Home Screen", style = MaterialTheme.typography.headlineMedium)
        GearListScreen(viewModel = viewModel)
    }

}

/**
 * Preview fuggveny a GearsHomeScreen-hoz.
 */
@Preview(showBackground = true)
@Composable
fun PreviewGearsHomeScreen() {
    //GearsHomeScreen(rememberNavController())
}