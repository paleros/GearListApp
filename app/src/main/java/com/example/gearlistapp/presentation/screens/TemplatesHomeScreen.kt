package com.example.gearlistapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.*

@Composable
fun TemplatesHomeScreen(navController: NavController) {
    Text("This is Templates Home Screen")
}

@Preview(showBackground = true)
@Composable
fun PreviewTemplatesHomeScreen() {
    TemplatesHomeScreen(rememberNavController())
}