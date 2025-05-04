package com.example.gearlistapp.presentation.dialogs.template

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gearlistapp.R
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gearlistapp.presentation.viewmodel.GearListState
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.TemplateViewModel
import com.example.gearlistapp.ui.common.ColorPickerDropdown
import com.example.gearlistapp.ui.common.GearSelector

/**
 * A sablon letrehozo dialogus
 * @param templateViewModel a sablon viewmodelje
 * @param gearViewModel a felszereles viewmodelje
 * @param onDismiss a dialogus bezarasa
 * @param onSave a sablon elmentese
 */
@Composable
fun TemplateCreateDialog(
    templateViewModel: TemplateViewModel = viewModel(factory = TemplateViewModel.Factory),
    gearViewModel: GearViewModel = viewModel(factory = GearViewModel.Factory),
    onDismiss: () -> Unit,
    onSave: (String, String, Int, SnapshotStateMap<Int, Boolean>, SnapshotStateMap<Int, String>, Int) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var backgroundColor by remember { mutableIntStateOf(Color.Gray.toArgb()) }
    val gearList = gearViewModel.state.collectAsStateWithLifecycle().value
    val selectedMap = remember { mutableStateMapOf<Int, Boolean>() }
    val piecesMap = remember { mutableStateMapOf<Int, String>() }

    /** Feltolti a listat az elemekkel*/
    when (gearList) {
        is GearListState.Loading -> {
        }

        is GearListState.Error -> {
        }

        is GearListState.Result -> {
            val gears = gearList.gearList

            if (!gears.isEmpty()) {
                gears.forEach { gear ->
                    if (gear.parent == -1){
                        selectedMap[gear.id] = false
                        piecesMap[gear.id] = "1"
                    }
                }
            }
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.add_new_template)) },
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
                    selectedMap = selectedMap,
                    piecesMap = piecesMap,
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(
                        title,
                        description,
                        duration.toIntOrNull() ?: 0,
                        selectedMap,
                        piecesMap,
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
