package com.example.gearlistapp.presentation.dialogs.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gearlistapp.R
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.ElectricalServices
import androidx.compose.material.icons.filled.Festival
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.toArgb

/**
 * A kategoria letrehozo dialogus
 * @param onDismiss a dialogus bezarasa
 * @param onSave a kategoria elmentese
 */
@Composable
fun CategoryCreateDialog(onDismiss: () -> Unit, onSave: (String, Int, ImageVector) -> Unit) {
    var name by remember { mutableStateOf("") }
    var color by remember { mutableIntStateOf(0) }
    var iconRes by remember { mutableStateOf(Icons.Default.Star) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.add_new_category)) },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(id = R.string.name)) },
                    modifier = Modifier.fillMaxWidth()
                )
                /** A szin valasztasa */
                Spacer(modifier = Modifier.height(8.dp))
                ColorPickerDropdown(
                    selectedColor = color,
                    onColorSelected = { color = it })
                /** Az ikon valasztasa */
                Spacer(modifier = Modifier.height(8.dp))
                IconPicker(onIconSelected = {iconRes = it})
            }
        },
        /** Mentes gomb */
        confirmButton = {
            TextButton(onClick = { onSave(name, color, iconRes)}) {
                Text(stringResource(id = R.string.save))
            }
        },
        /** Megse gomb */
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}

/**
 * Szin kivalaszto menu
 * @param selectedColor a kivalasztott szin
 * @param onColorSelected a szin kivalasztasa
 */
@Composable
fun ColorPickerDropdown(selectedColor: Int, onColorSelected: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    /** A valaszthato szinek listaja */
    val colors = listOf(
        Color(0xFF231F20),
        Color(0xFF4A4A4A),
        Color(0xFF8B3A3A),
        Color(0xFF2E7D60),
        Color(0xFF1E4E79),
        Color(0xFFD9B572),
        Color(0xFFC47E3A),
        Color(0xFF5A3E6B),
        Color(0xFFE5C1B1),
        Color(0xFF704214),
        Color(0xFF87A6A3),
        Color(0xFFB35A9C),
        Color(0xFF66A676),
        Color(0xFF0D3058),
        Color(0xFF427A6B),
        Color(0xFF8F865A),
        Color(0xFF5B3223),
        Color(0xFFE6C266),
        Color(0xFFB0AFAF)
    )

    Text(
        text = stringResource(id = R.string.color),
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.padding(8.dp)
    )
    Box {
        Button(onClick = { expanded = true }) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color(selectedColor), shape = CircleShape)
            )
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            colors.forEach { color ->
                DropdownMenuItem(
                    text = { Text(text = " ", modifier = Modifier.background(color)) },
                    onClick = {
                        onColorSelected(color.toArgb())
                        expanded = false
                    },
                    modifier = Modifier.background(color)
                )
            }
        }
    }
}

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