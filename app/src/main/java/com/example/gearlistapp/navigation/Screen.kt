package com.example.gearlistapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.BeachAccess
import androidx.compose.material.icons.filled.Hiking
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.gearlistapp.R

/**
 * A screen-ek navigaciojat reprezentalo osztaly.
 * @property route a navigacios elem azonositoja.
 * @property title a navigacios elem cime.
 * @property icon a navigacios elem ikonja.
 */
sealed class Screen(val route: String, val title: Int, val icon: ImageVector) {
    /** A HomeScreen osztalya (kezdokepernyo, itt jelennek meg az aktualis felszereleslistak).*/
    object HomeScreen : Screen("home_screen", R.string.gear_lists, Icons.Default.Hiking)
    /** A GearsHomeScreen osztalya (felszerelesek kezdokepernyoje).*/
    object GearsHomeScreen : Screen("gears_home_screen", R.string.gears, Icons.Default.BeachAccess)
    /** A TemplatesHomeScreen osztalya (felszereleslista sablonok kezdokepernyoje).*/
    object TemplatesHomeScreen : Screen("templates_home_screen", R.string.templates, Icons.Default.Backpack)
}

/** A home screen-ek listaja, a TopAppBar megjeleniteshez.*/
val homeScreens = listOf(Screen.HomeScreen, Screen.GearsHomeScreen, Screen.TemplatesHomeScreen)