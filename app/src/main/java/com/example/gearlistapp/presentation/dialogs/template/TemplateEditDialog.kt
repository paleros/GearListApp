package com.example.gearlistapp.presentation.dialogs.template

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gearlistapp.R
import com.example.gearlistapp.data.model.Gear
import com.example.gearlistapp.data.model.Template
import com.example.gearlistapp.presentation.viewmodel.GearListState
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.TemplateViewModel
import com.example.gearlistapp.ui.common.ColorPickerDropdown
import com.example.gearlistapp.ui.common.GearSelector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.collections.set

/**
 * A sablon modosito dialogus
 * @param templateId a sablon azonositoja
 * @param templateViewModel a sablon viewmodelje
 * @param gearViewModel a felszereles viewmodelje
 * @param onDismiss a dialogus bezarasa
 * @param onEdit a sablon modositasa
 */
@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun TemplateEditDialog(
    templateId: Int,
    templateViewModel: TemplateViewModel = viewModel(factory = TemplateViewModel.Factory),
    gearViewModel: GearViewModel = viewModel(factory = GearViewModel.Factory),
    onDismiss: () -> Unit,
    onEdit: (Int, String, String, Int, SnapshotStateMap<Int, Boolean>, SnapshotStateMap<Int, String>, Int, String) -> Unit
) {
    //TODO a felszereles lista modositasokor nem mindig ment helyesen
    var currentTemplate by remember { mutableStateOf<Template?>(null) }
    var title by remember { mutableStateOf<String>("") }
    var description by remember { mutableStateOf<String>("") }
    var duration by remember { mutableStateOf("") }
    var backgroundColor by remember { mutableIntStateOf(Color.White.toArgb()) }
    var itemList by remember { mutableStateOf(emptyList<Int>()) }
    var date by remember { mutableStateOf("") }
    var concrete by remember { mutableStateOf(false) }

    var currentSelectedMap = remember { mutableStateMapOf<Int, Boolean>() }
    var currentPiecesMap = remember { mutableStateMapOf<Int, String>() }
    var currentSelectedGearParentId = remember { mutableStateListOf<Int>() }

    var titleIsError by remember { mutableStateOf(false) }
    var dayIsError by remember { mutableStateOf(false) }

    LaunchedEffect(templateId) {
        val template = suspendCancellableCoroutine<Template?> { continuation ->
            templateViewModel.getById(templateId) { result ->
                continuation.resume(result, onCancellation = null)
            }
        }

        template?.let {
            currentTemplate = it
            title = it.title
            description = it.description
            duration = it.duration.toString()
            backgroundColor = it.backgroundColor
            itemList = it.itemList
            date = it.date
            concrete = it.concrete

            val gearState = gearViewModel.state.first { state ->
                state is GearListState.Result } as GearListState.Result
            val gearList = gearState.gearList

            currentSelectedGearParentId.clear()
            currentSelectedMap.clear()
            currentPiecesMap.clear()

            for (gear in gearList) {
                if (gear.parent == -1) {
                    /** Minden gear id bekerul a listaba, ha nem gyerek */
                    currentSelectedMap[gear.id] = false
                    currentPiecesMap[gear.id] = "1"
                }
            }

            /** A sablon alapjan kikeresi a kijelolt felszerelesek szuleit */
            for (id in it.itemList) {
                val gear = suspendCancellableCoroutine<Gear?> { cont ->
                    gearViewModel.getById(id) { result ->
                        cont.resume(result, onCancellation = null)
                    }
                }
                val parentId = gear?.parent ?: -1
                currentSelectedGearParentId.add(parentId)
                currentPiecesMap[parentId] = gear?.pieces.toString()
            }

            /** Amleiyk gear korabban ki volt valasztva az bekerul a valasztott gearok listajaba */
            for (id in currentSelectedGearParentId) {
                currentSelectedMap[id] = true
            }
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.edit_template)) },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it
                        titleIsError = title.isBlank()},
                    label = { Text(stringResource(id = R.string.template_title)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = titleIsError,
                    singleLine = true
                )
                if (titleIsError) {
                    Text(
                        text = stringResource(id = R.string.this_field_is_required),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

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
                    onValueChange = { duration = it.filter { char -> char.isDigit() }
                        dayIsError = duration.isBlank()},
                    label = { Text(stringResource(id = R.string.duration_day)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    isError = dayIsError
                )
                if (dayIsError) {
                    Text(
                        text = stringResource(id = R.string.this_field_is_required),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                //TODO datumvalaszto, ha konkret

                Spacer(modifier = Modifier.height(8.dp))
                ColorPickerDropdown(
                    selectedColor = backgroundColor,
                    onColorSelected = { backgroundColor = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
                GearSelector(
                    selectedMap = currentSelectedMap,
                    piecesMap = currentPiecesMap,
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    titleIsError = title.isBlank()
                    dayIsError = duration.isBlank()
                    if (!titleIsError && !dayIsError) {
                        onEdit(
                            templateId,
                            title,
                            description,
                            duration.toIntOrNull() ?: 0,
                            currentSelectedMap,
                            currentPiecesMap,
                            backgroundColor,
                            date
                        )
                        if (!concrete) {
                            for (id in itemList) {
                                gearViewModel.getById(id) {
                                    gearViewModel.delete(id)
                                }
                            }
                        }
                    }
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
