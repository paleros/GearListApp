package com.example.gearlistapp.presentation.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

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
