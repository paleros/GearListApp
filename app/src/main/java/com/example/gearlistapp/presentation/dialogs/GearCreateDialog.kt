package com.example.gearlistapp.presentation.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExposedDropdownMenuBox
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gearlistapp.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Icon
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.gearlistapp.presentation.screens.stringToImageVector
import com.example.gearlistapp.presentation.viewmodel.CategoryListState
import com.example.gearlistapp.presentation.viewmodel.CategoryViewModel
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.LocationListState
import com.example.gearlistapp.presentation.viewmodel.LocationViewModel
import com.example.gearlistapp.ui.model.toUiText

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
    gearViewModel: GearViewModel = viewModel(factory = GearViewModel.Factory),
    categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.Factory),
    locationViewModel: LocationViewModel = viewModel(factory = LocationViewModel.Factory),
    onDismiss: () -> Unit, onSave: (String, String, Int, Int) -> Unit) {

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var categoryId by remember { mutableStateOf("") }
    var locationId by remember { mutableStateOf("") }

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
                    onValueChange = { name = it },
                    label = { Text(stringResource(id = R.string.name)) },
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
                /*TextField(
                    value = categoryId,
                    onValueChange = { categoryId = it },
                    label = { Text(stringResource(id = R.string.category)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false
                )*/
                CategoryDropdown(
                    categoryViewModel = categoryViewModel,
                    gearViewModel = gearViewModel,
                    locationViewModel = locationViewModel,
                    onCategorySelected = { categoryId = it.toString() }
                )
                Spacer(modifier = Modifier.height(8.dp))
                LocationDropdown(
                    categoryViewModel = categoryViewModel,
                    gearViewModel = gearViewModel,
                    locationViewModel = locationViewModel,
                    onLocationSelected = { locationId = it.toString() }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onSave(name, description, categoryId.toInt(), locationId.toInt()) }) {
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

/**
 * A helyszin kivalaszto menu
 * @param gearViewModel a felszereles viewmodelje
 * @param categoryViewModel a kategoria viewmodelje
 * @param locationViewModel a helyszin viewmodelje
 * @param onLocationSelected a helyszin kivalasztasa
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDropdown(
    gearViewModel: GearViewModel = viewModel(factory = GearViewModel.Factory),
    categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.Factory),
    locationViewModel: LocationViewModel = viewModel(factory = LocationViewModel.Factory),
    onLocationSelected: (Int) -> Unit
) {

    val locationList = locationViewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedLocation by remember { mutableStateOf<String>("") }

    LaunchedEffect(Unit) {
        locationViewModel.loadLocations()
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            value = selectedLocation,
            onValueChange = {},
            label = { Text(stringResource(id = R.string.location)) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth().menuAnchor(),
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
            }
        )

        when (locationList) {
            is LocationListState.Loading -> CircularProgressIndicator(
                color = MaterialTheme.colorScheme.secondaryContainer
            )

            is LocationListState.Error -> Text(
                text = locationList.error.toUiText().asString(context)
            )

            is LocationListState.Result -> {
                if (locationList.locationList.isNotEmpty()) {
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.exposedDropdownSize()
                    ) {
                        Column(
                        ) {
                            locationList.locationList.forEach { location ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedLocation = location.name
                                        onLocationSelected(location.id)
                                        expanded = false
                                    },
                                    text = { Text(location.name) },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    gearViewModel: GearViewModel = viewModel(factory = GearViewModel.Factory),
    categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.Factory),
    locationViewModel: LocationViewModel = viewModel(factory = LocationViewModel.Factory),
    onCategorySelected: (Int) -> Unit
) {

    val categoryList = categoryViewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<String>("") }

    LaunchedEffect(Unit) {
        categoryViewModel.loadCategories()
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            value = selectedCategory,
            onValueChange = {},
            label = { Text(stringResource(id = R.string.category)) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth().menuAnchor(),
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
            }
        )

        when (categoryList) {
            is CategoryListState.Loading -> CircularProgressIndicator(
                color = MaterialTheme.colorScheme.secondaryContainer
            )

            is CategoryListState.Error -> Text(
                text = categoryList.error.toUiText().asString(context)
            )

            is CategoryListState.Result -> {
                if (categoryList.categoryList.isNotEmpty()) {
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.exposedDropdownSize()
                    ) {
                        Column(
                        ) {
                            categoryList.categoryList.forEach { category ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedCategory = category.name
                                        onCategorySelected(category.id)
                                        expanded = false
                                    },
                                    text = { Text(category.name) },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = stringToImageVector(category.iconName),
                                            contentDescription = "Category Icon",
                                        )
                                    },
                                    modifier = Modifier.background(Color(category.color)),
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}