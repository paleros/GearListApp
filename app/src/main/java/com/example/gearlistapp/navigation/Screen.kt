package com.example.gearlistapp.navigation

import androidx.annotation.DrawableRes
import com.example.gearlistapp.R

/**
 * A screen-ek navigaciojat reprezentalo osztaly.
 * @property route a navigacios elem azonositoja.
 * @property title a navigacios elem cime.
 * @property iconRes a navigacios elem ikonja.
 */
sealed class Screen(val route: String, val title: String, @DrawableRes val iconRes: Int) {
    /** A HomeScreen osztalya (kezdokepernyo, itt jelennek meg az aktualis felszereleslistak).*/
    object HomeScreen : Screen("home_screen", "Gear lists", R.drawable.baseline_hiking_24)
    /** A GearsHomeScreen osztalya (felszerelesek kezdokepernyoje).*/
    object GearsHomeScreen : Screen("gears_home_screen", "Gears", R.drawable.baseline_beach_access_24)
    /** A TemplatesHomeScreen osztalya (felszereleslista sablonok kezdokepernyoje).*/
    object TemplatesHomeScreen : Screen("templates_home_screen", "Templates", R.drawable.baseline_all_inbox_24)
}

/** A home screen-ek listaja, a TopAppBar megjeleniteshez.*/
val homeScreens = listOf(Screen.HomeScreen, Screen.GearsHomeScreen, Screen.TemplatesHomeScreen)