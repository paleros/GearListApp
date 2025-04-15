package com.example.gearlistapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.ElectricalServices
import androidx.compose.material.icons.filled.Festival
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gearlistapp.R

/**
 * Ikon kivalaszto menu
 * @param onIconSelected az ikon kivalasztasa
 */
@Composable
fun IconPicker(
    onIconSelected: (ImageVector) -> Unit
) {

    var selectedIcon by remember { mutableStateOf<ImageVector?>(null) }

    /** A valaszthato ikonok listaja */
    val icons = listOf(
        Icons.Default.Star,
        Icons.Default.Festival,
        Icons.Default.Backpack,
        Icons.Default.ElectricalServices,
        Icons.Default.Checkroom,
        Icons.Default.WaterDrop,
        Icons.Default.RocketLaunch,
        Icons.Default.Cookie,
    )

    Text(
        text = stringResource(id = R.string.icon),
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.padding(8.dp)
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(icons) { icon ->
            val iconButtonColor = if (selectedIcon == icon) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onPrimary
            }

            IconButton(
                onClick = { selectedIcon = icon
                    onIconSelected(icon) },
                modifier = Modifier
                    .padding(8.dp)
                    .size(48.dp)
                    .background(
                        color = iconButtonColor,
                        shape = CircleShape
                    ),
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}