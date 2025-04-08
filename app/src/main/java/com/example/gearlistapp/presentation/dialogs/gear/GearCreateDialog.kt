package com.example.gearlistapp.presentation.dialogs.gear

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Room
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.gearlistapp.presentation.screens.gear.stringToImageVector
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
                CategoryDropdown(
                    categoryViewModel = categoryViewModel,
                    gearViewModel = gearViewModel,
                    locationViewModel = locationViewModel,
                    onCategorySelected = { categoryId = it.toString() },
                )
                Spacer(modifier = Modifier.height(8.dp))
                LocationDropdown(
                    categoryViewModel = categoryViewModel,
                    gearViewModel = gearViewModel,
                    locationViewModel = locationViewModel,
                    onLocationSelected = { locationId = it.toString() },
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
 * @param previousLocation a korabban kivalasztott helyszin
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDropdown(
    gearViewModel: GearViewModel = viewModel(factory = GearViewModel.Factory),
    categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.Factory),
    locationViewModel: LocationViewModel = viewModel(factory = LocationViewModel.Factory),
    onLocationSelected: (Int) -> Unit,
    previousLocation: String = "null",
) {

    val locationList = locationViewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    var selectedLocation by remember { mutableStateOf<String>("") }

    LaunchedEffect(Unit) {
        if (previousLocation != "null") {
            locationViewModel.getNameById(id = previousLocation.toInt(),
                onResult = { selectedLocation = it })
        }

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
            modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true),
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
            },
            leadingIcon = {
                Box(
                    modifier = Modifier.size(35.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.fillMaxSize()
                    )
                    Icon(
                        imageVector = Icons.Default.Room,
                        contentDescription = "Location Icon",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
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
                        Column {
                            locationList.locationList.forEach { location ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedLocation = location.name
                                        onLocationSelected(location.id)
                                        expanded = false
                                    },
                                    text = { Text(location.name) },
                                    leadingIcon = {
                                        Box(
                                            modifier = Modifier.size(35.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Circle,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.tertiary,
                                                modifier = Modifier.fillMaxSize()
                                            )
                                            Icon(
                                                imageVector = Icons.Default.Room,
                                                contentDescription = "Location Icon",
                                                tint = Color.White,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    },
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

/**
 * A helyszin kivalaszto menu
 * @param gearViewModel a felszereles viewmodelje
 * @param categoryViewModel a kategoria viewmodelje
 * @param locationViewModel a helyszin viewmodelje
 * @param onCategorySelected a kategoria kivalasztasa
 * @param previousCategory a korabban kivalasztott kategoria
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    gearViewModel: GearViewModel = viewModel(factory = GearViewModel.Factory),
    categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.Factory),
    locationViewModel: LocationViewModel = viewModel(factory = LocationViewModel.Factory),
    onCategorySelected: (Int) -> Unit,
    previousCategory: String = "null",
) {

    val categoryList = categoryViewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    var selectedCategoryName by remember { mutableStateOf<String>("") }
    var selectedCategoryIconName by remember { mutableStateOf<String>("") }
    var selectedCategoryColor by remember { mutableIntStateOf(Color.Gray.toArgb()) }

    LaunchedEffect(Unit) {
        if (previousCategory != "null") {
            categoryViewModel.getNameById(id = previousCategory.toInt(),
                                        onResult = { selectedCategoryName = it })
            categoryViewModel.getIconNameById(id = previousCategory.toInt(),
                                        onResult = { selectedCategoryIconName = it })
            categoryViewModel.getColorById(id = previousCategory.toInt(),
                                        onResult = { selectedCategoryColor = it })
        }

        categoryViewModel.loadCategories()
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            value = selectedCategoryName,
            onValueChange = {},
            label = { Text(stringResource(id = R.string.category)) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true),
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
            },
            leadingIcon = {
                Box(
                    modifier = Modifier.size(35.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = null,
                        tint = Color(selectedCategoryColor),
                        modifier = Modifier.fillMaxSize()
                    )
                    Icon(
                        imageVector = stringToImageVector(selectedCategoryIconName),
                        contentDescription = "Category Icon",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
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
                        Column {
                            categoryList.categoryList.forEach { category ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedCategoryName = category.name
                                        selectedCategoryIconName = category.iconName
                                        selectedCategoryColor = category.color
                                        onCategorySelected(category.id)
                                        expanded = false
                                    },
                                    text = { Text(category.name) },
                                    leadingIcon = {
                                        Box(
                                            modifier = Modifier.size(35.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Circle,
                                                contentDescription = null,
                                                tint = Color(category.color),
                                                modifier = Modifier.fillMaxSize()
                                            )
                                            Icon(
                                                imageVector = stringToImageVector(category.iconName),
                                                contentDescription = "Category Icon",
                                                tint = Color.White,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    },
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