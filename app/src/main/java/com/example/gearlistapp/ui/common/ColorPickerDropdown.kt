package com.example.gearlistapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gearlistapp.R

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