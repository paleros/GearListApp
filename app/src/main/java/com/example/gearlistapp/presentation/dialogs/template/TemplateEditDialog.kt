package com.example.gearlistapp.presentation.dialogs.template

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gearlistapp.R
import com.example.gearlistapp.presentation.viewmodel.TemplateViewModel
import com.example.gearlistapp.ui.common.ColorPickerDropdown
import com.example.gearlistapp.ui.common.GearSelector

@Composable
fun TemplateEditDialog(
    templateId: Int,
    currentTitle: String,
    currentDescription: String,
    currentDuration: Int,
    currentColor: Int,
    currentGearIds: List<Int>,
    templateViewModel: TemplateViewModel = viewModel(factory = TemplateViewModel.Factory),
    onDismiss: () -> Unit,
    onEdit: (Int, String, String, Int, List<Int>, Int) -> Unit
) {
    var title by remember { mutableStateOf(currentTitle) }
    var description by remember { mutableStateOf(currentDescription) }
    var duration by remember { mutableStateOf(currentDuration.toString()) }
    var backgroundColor by remember { mutableIntStateOf(currentColor) }
    val itemList = remember { mutableStateListOf<Int>().apply { addAll(currentGearIds) } }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.edit_template)) },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(stringResource(id = R.string.template_title)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(id = R.string.description)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = duration,
                    onValueChange = { duration = it.filter { char -> char.isDigit() } },
                    label = { Text(stringResource(id = R.string.duration_day)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                ColorPickerDropdown(
                    selectedColor = backgroundColor,
                    onColorSelected = { backgroundColor = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
                GearSelector(
                    selectedGearIds = itemList,
                    onSelectionChanged = { selectedIds ->
                        itemList.clear()
                        itemList.addAll(selectedIds)
                    }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onEdit(
                        templateId,
                        title,
                        description,
                        duration.toIntOrNull() ?: 0,
                        itemList.toList(),
                        backgroundColor
                    )
                }
            ) {
                Text(stringResource(id = R.string.save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}
