package com.example.gearlistapp.ui.common

import android.R.attr.tint
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.gearlistapp.R
import com.example.gearlistapp.navigation.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {
    val screenItems = listOf(
        Screen.TemplatesHomeScreen,
        Screen.HomeScreen,
        Screen.GearsHomeScreen
    )

    NavigationBar (containerColor = MaterialTheme.colorScheme.secondary) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        screenItems.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(painter = painterResource(id = screen.iconRes), contentDescription = screen.title,
                    tint = MaterialTheme.colorScheme.onSecondary) },
                label = { Text(screen.title, color = MaterialTheme.colorScheme.onSecondary) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                    }
                }
            )
        }
    }
}