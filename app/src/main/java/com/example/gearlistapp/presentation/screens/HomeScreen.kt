package com.example.gearlistapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gearlistapp.GearApplication.Companion.gearRepository
import com.example.gearlistapp.presentation.viewmodel.GearViewModel

/**
 * A főképernyőt, azaz az aktualis felszereleslistakat reprezentáló komponens.
 * @param navController a navigációs controller
 */
@Composable
fun HomeScreen() {
    Text("This is Gear List Home Screen")
}

/**
 * Preview fuggveny a HomeScreen-hoz.
 */
@Preview(showBackground = true)
@Composable
fun PreviewGearListsHomeScreen() {
    HomeScreen()
}
