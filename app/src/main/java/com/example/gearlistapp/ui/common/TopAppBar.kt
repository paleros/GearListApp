package com.example.gearlistapp.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gearlistapp.navigation.homeScreens

/**
 * A felso app savot reprezentalo komponens.
 * @param navController a navigacios controller
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = homeScreens.find { it.route == navBackStackEntry.value?.destination?.route } ?: homeScreens.first()

    var menuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        /** A cim adatai, ikon es szoveg. */
        title = {
            Row {
                Image(painter = painterResource(id = currentScreen.iconRes),
                    contentDescription = "Screen Icon",
                    modifier = Modifier.size(30.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = currentScreen.title)
            }
        },
        /** A menu ikon. */
        navigationIcon = {
            IconButton(onClick = { menuExpanded = true }) {
                Icon(Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onPrimary)
            }
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(text = { Text("Option 1") }, onClick = { /*TODO menü opció*/ })
                DropdownMenuItem(text = { Text("Option 2") }, onClick = { /*TODO menü opció*/ })
            }
        },
        /** A kereso ikon. */
        actions = {
            IconButton(onClick = { /*TODO keresés */ }) {
                Icon(Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
    )
}

/**
 * Preview fuggveny a TopAppBar-hoz.
 */
@Preview
@Composable
fun TopAppBarPreview() {
    TopAppBar(rememberNavController())
}