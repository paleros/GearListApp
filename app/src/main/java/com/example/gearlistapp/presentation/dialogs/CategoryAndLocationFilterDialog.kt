package com.example.gearlistapp.presentation.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gearlistapp.R
import com.example.gearlistapp.presentation.viewmodel.CategoryViewModel
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.LocationViewModel
import com.example.gearlistapp.ui.common.CategoryDropdown
import com.example.gearlistapp.ui.common.LocationDropdown

/**
 * Filter beallitasat vegzo dialogus
 * @param onDismiss a dialogus bezarasa
 * @param onCategorySelected a kategoria valasztasa
 * @param onLocationSelected a helyszin valasztasa
 * @param onDeleteFilters a filter torlese
 * @param gearViewModel a felszereles viewmodelje
 * @param categoryViewModel a kategoria viewmodelje
 * @param locationViewModel a helyszin viewmodelje
 * @param previousCategory az elozo kategoria
 * @param previousLocation az elozo helyszin
 */
@Composable
fun CategoryAndLocationFilterDialog(onDismiss: () -> Unit,
                                    onCategorySelected: (String) -> Unit,
                                    onLocationSelected: (String) -> Unit,
                                    onDeleteFilters: () -> Unit,
                                    gearViewModel: GearViewModel = viewModel(factory = GearViewModel.Factory),
                                    categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.Factory),
                                    locationViewModel: LocationViewModel = viewModel(factory = LocationViewModel.Factory),
                                    previousCategory: String = "null",
                                    previousLocation: String = "null",
) {

    var selectedLocation by remember { mutableStateOf<String>("null") }
    var selectedCategory by remember { mutableStateOf<String>("null") }

    LaunchedEffect(Unit) {
        if (previousCategory != "null") {
            selectedCategory = previousCategory
        }
        if (previousLocation != "null") {
            selectedLocation = previousLocation
        }
    }

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
        title = { Text(text = stringResource(id = R.string.filter)) },
        text = {
            Column {
                CategoryDropdown(
                    categoryViewModel = categoryViewModel,
                    gearViewModel = gearViewModel,
                    locationViewModel = locationViewModel,
                    previousCategory = previousCategory,
                    onCategorySelected = { selectedCategory = it.toString()},
                )
                Spacer(modifier = Modifier.height(8.dp))
                LocationDropdown(
                    categoryViewModel = categoryViewModel,
                    gearViewModel = gearViewModel,
                    locationViewModel = locationViewModel,
                    previousLocation = previousLocation,
                    onLocationSelected = { selectedLocation = it.toString() },
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onDeleteFilters() }) {
                Text(text = stringResource(id = R.string.delete_filters))
            }
            TextButton(onClick = {
                onCategorySelected(selectedCategory.toString())
                onLocationSelected(selectedLocation.toString())
                onDismiss()
            }) {
                Text(text = stringResource(id = R.string.use))
            }
        },
    )
}