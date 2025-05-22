package com.example.gearlistapp.presentation.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.gearlistapp.R
import com.example.gearlistapp.presentation.viewmodel.CategoryViewModel
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.LocationViewModel
import com.example.gearlistapp.ui.common.LocationDropdown

/**
 * Filter beallitasat vegzo dialogus
 * @param onDismiss a dialogus bezarasa
 * @param onLocationSelected a kategoria valasztasa
 * @param gearViewModel a felszereles viewmodelje
 * @param locationViewModel a kategoria viewmodelje
 * @param categoryViewModel a helyszin viewmodelje
 * @param previousLocation az elozo kategoria
 * @param onDeleteFilters a filter torlese
 */
@Composable
fun LocationFilterDialog(onDismiss: () -> Unit,
                                    onLocationSelected: (String) -> Unit,
                                    onDeleteFilters: () -> Unit,
                                    gearViewModel: GearViewModel,
                                    locationViewModel: LocationViewModel,
                                    categoryViewModel: CategoryViewModel,
                                    previousLocation: String = "null",
) {

    var selectedLocation by remember { mutableStateOf<String>("null") }

    LaunchedEffect(Unit) {
        if (previousLocation != "null") {
            selectedLocation = previousLocation
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.filter)) },
        text = {
            Column {
                LocationDropdown(
                    locationViewModel = locationViewModel,
                    gearViewModel = gearViewModel,
                    previousLocation = previousLocation,
                    onLocationSelected = { selectedLocation = it.toString()},
                    categoryViewModel = categoryViewModel,
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onDeleteFilters() }) {
                Text(text = stringResource(id = R.string.delete_filters))
            }
            TextButton(onClick = {
                onLocationSelected(selectedLocation.toString())
                onDismiss()
            }) {
                Text(text = stringResource(id = R.string.use))
            }
        },
    )
}