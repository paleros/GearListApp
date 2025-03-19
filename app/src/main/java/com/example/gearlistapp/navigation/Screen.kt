package com.example.gearlistapp.navigation

import androidx.annotation.DrawableRes
import com.example.gearlistapp.R

sealed class Screen(val route: String, val title: String, @DrawableRes val iconRes: Int) {
    object HomeScreen : Screen("home_screen", "Gear lists", R.drawable.baseline_hiking_24)
    object GearsHomeScreen : Screen("gears_home_screen", "Gears", R.drawable.baseline_beach_access_24)
    object TemplatesHomeScreen : Screen("templates_home_screen", "Templates", R.drawable.baseline_all_inbox_24)
}

val screens = listOf(Screen.HomeScreen, Screen.GearsHomeScreen, Screen.TemplatesHomeScreen)