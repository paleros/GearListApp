package com.example.gearlistapp.presentation.dialogs.gear

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gearlistapp.R
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.gearlistapp.presentation.viewmodel.CategoryViewModel
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.LocationViewModel
import com.example.gearlistapp.ui.common.CategoryDropdown
import com.example.gearlistapp.ui.common.LocationDropdown

/**
 * A felszereles letrehozo dialogus
 * @param gearViewModel a felszereles viewmodelje
 * @param categoryViewModel a kategoria viewmodelje
 * @param locationViewModel a helyszin viewmodelje
 * @param onDismiss a dialogus bezarasa
 * @param onSave a felszereles elmentese
 */
@Composable
fun GearCreateDialog(
    gearViewModel: GearViewModel,
    categoryViewModel: CategoryViewModel,
    locationViewModel: LocationViewModel,
    onDismiss: () -> Unit, onSave: (String, String, Int, Int) -> Unit) {

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var categoryId by remember { mutableStateOf("") }
    var locationId by remember { mutableStateOf("") }
    var nameIsError by remember { mutableStateOf(false) }
    var categoryIsError = remember { mutableStateOf(false) }
    var locationIsError = remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                locationViewModel.loadLocations()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.add_new_gear)) },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = {
                        name = it
                        nameIsError = it.isBlank()},
                    label = { Text(stringResource(id = R.string.name)) },
                    isError = nameIsError,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                if (nameIsError) {
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
                CategoryDropdown(
                    categoryViewModel = categoryViewModel,
                    gearViewModel = gearViewModel,
                    locationViewModel = locationViewModel,
                    onCategorySelected = {
                        categoryId = it.toString()},
                    isError = categoryIsError,
                )
                Spacer(modifier = Modifier.height(8.dp))
                LocationDropdown(
                    categoryViewModel = categoryViewModel,
                    gearViewModel = gearViewModel,
                    locationViewModel = locationViewModel,
                    onLocationSelected = {
                        locationId = it.toString()},
                    isError = categoryIsError,
                    )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                nameIsError = name.isBlank()
                categoryIsError.value = categoryId.isBlank()
                locationIsError.value = locationId.isBlank()
                if (!nameIsError && !categoryIsError.value && !locationIsError.value) {
                    onSave(name, description, categoryId.toInt(), locationId.toInt())
                } }) {
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