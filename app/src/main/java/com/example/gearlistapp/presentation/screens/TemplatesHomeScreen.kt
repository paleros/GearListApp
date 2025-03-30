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
import androidx.navigation.compose.*
import com.example.gearlistapp.GearApplication.Companion.gearRepository
import com.example.gearlistapp.presentation.viewmodel.GearViewModel

/**
 * A sablonok fokepernyojet reprezentalo komponens.
 * @param navController a navigacios controller
 */
@Composable
fun TemplatesHomeScreen() {
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