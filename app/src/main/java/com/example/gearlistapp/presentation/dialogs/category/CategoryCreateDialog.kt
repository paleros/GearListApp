package com.example.gearlistapp.presentation.dialogs.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.gearlistapp.ui.common.ColorPickerDropdown
import com.example.gearlistapp.ui.common.IconPicker

/**
 * A kategoria letrehozo dialogus
 * @param onDismiss a dialogus bezarasa
 * @param onSave a kategoria elmentese
 */
@Composable
fun CategoryCreateDialog(onDismiss: () -> Unit, onSave: (String, Int, ImageVector) -> Unit) {
    var name by remember { mutableStateOf("") }
    var color by remember { mutableIntStateOf(Color.Gray.toArgb()) }
    var iconRes by remember { mutableStateOf(Icons.Default.Star) }
    var isError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.add_new_category)) },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = {
                        name = it
                        isError = it.isBlank()},
                    label = { Text(stringResource(id = R.string.name)) },
                    isError = isError,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                if (isError) {
                    Text(
                        text = stringResource(id = R.string.this_field_is_required),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                /** A szin valasztasa */
                Spacer(modifier = Modifier.height(8.dp))
                ColorPickerDropdown(
                    selectedColor = color,
                    onColorSelected = { color = it })
                /** Az ikon valasztasa */
                Spacer(modifier = Modifier.height(8.dp))
                IconPicker(
                    selectedIcon = iconRes,
                    onIconSelected = {iconRes = it})
            }
        },
        /** Mentes gomb */
        confirmButton = {
            TextButton(onClick = {
                isError = name.isBlank()
                if (!isError) {
                    onSave(name, color, iconRes)
                }
            }) {
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