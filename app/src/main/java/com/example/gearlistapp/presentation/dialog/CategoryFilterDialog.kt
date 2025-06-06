package com.example.gearlistapp.presentation.dialog

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
import com.example.gearlistapp.ui.common.CategoryDropdown

/**
 * Filter beallitasat vegzo dialogus
 * @param onDismiss a dialogus bezarasa
 * @param onCategorySelected a kategoria valasztasa
 * @param gearViewModel a felszereles viewmodelje
 * @param categoryViewModel a kategoria viewmodelje
 * @param locationViewModel a helyszin viewmodelje
 * @param previousCategory az elozo kategoria
 * @param onDeleteFilters a filter torlese
 */
@Composable
fun CategoryFilterDialog(onDismiss: () -> Unit,
                                    onCategorySelected: (String) -> Unit,
                                    onDeleteFilters: () -> Unit,
                                    gearViewModel: GearViewModel,
                                    categoryViewModel: CategoryViewModel,
                                    locationViewModel: LocationViewModel,
                                    previousCategory: String = "null",
) {

    var selectedCategory by remember { mutableStateOf<String>("null") }

    LaunchedEffect(Unit) {
        if (previousCategory != "null") {
            selectedCategory = previousCategory
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
                    previousCategory = previousCategory,
                    onCategorySelected = { selectedCategory = it.toString()},
                    locationViewModel = locationViewModel,
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onDeleteFilters() }) {
                Text(text = stringResource(id = R.string.delete_filters))
            }
            TextButton(onClick = {
                onCategorySelected(selectedCategory.toString())
                onDismiss()
            }) {
                Text(text = stringResource(id = R.string.use))
            }
        },
    )
}