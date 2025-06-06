package com.example.gearlistapp.ui.common

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.gearlistapp.navigation.Screen

/**
 * Az also navigacios savot reprezentalo komponens.
 * @param navController a navigacios controller
 */
@Composable
fun BottomNavigationBar(navController: NavController) {
    /** Az ikonokhoz tartozo elemek listaja. */
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
                icon = { Icon(screen.icon,
                    contentDescription = stringResource(id = screen.title),
                    tint = MaterialTheme.colorScheme.onSecondary)
                },
                label = { Text(stringResource(id =screen.title),
                    color = MaterialTheme.colorScheme.onSecondary) },
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