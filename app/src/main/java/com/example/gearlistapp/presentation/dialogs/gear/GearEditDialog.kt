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
import androidx.lifecycle.viewmodel.compose.viewModel
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
 * A felszerelest szerkeszto dialogus
 * @param gearId a felszereles azonositoja
 * @param currentName a korabi nev
 * @param currentDescription a korabbi leiras
 * @param currentCategoryId a korabbi kategoria azonosito
 * @param currentLocationId a korabbi helyszin azonosito
 * @param gearViewModel a felszereles viewmodelje
 * @param categoryViewModel a kategoria viewmodelje
 * @param locationViewModel a helyszin viewmodelje
 * @param onDismiss a bezaras
 * @param onEdit a szerkesztes
 */
@Composable
fun GearEditDialog(
    gearId: Int,
    currentName: String,
    currentDescription: String,
    currentCategoryId: Int,
    currentLocationId: Int,
    gearViewModel: GearViewModel,
    categoryViewModel: CategoryViewModel,
    locationViewModel: LocationViewModel,
    onDismiss: () -> Unit,
    onEdit: (Int, String, String, Int, Int) -> Unit
) {
    var name by remember { mutableStateOf(currentName) }
    var description by remember { mutableStateOf(currentDescription) }
    var categoryId by remember { mutableStateOf(currentCategoryId.toString()) }
    var locationId by remember { mutableStateOf(currentLocationId.toString()) }
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
        title = { Text(text = stringResource(id = R.string.edit_gear)) },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it
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
                    onCategorySelected = { categoryId = it.toString() },
                    previousCategory = currentCategoryId.toString(),
                    isError = categoryIsError,
                    )
                Spacer(modifier = Modifier.height(8.dp))
                LocationDropdown(
                    categoryViewModel = categoryViewModel,
                    gearViewModel = gearViewModel,
                    locationViewModel = locationViewModel,
                    onLocationSelected = { locationId = it.toString() },
                    previousLocation = currentLocationId.toString(),
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
                    onEdit(
                        gearId,
                        name,
                        description,
                        categoryId.toInt(),
                        locationId.toInt()
                    )
                }
            }) {
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