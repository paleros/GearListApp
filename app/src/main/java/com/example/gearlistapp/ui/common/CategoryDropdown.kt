package com.example.gearlistapp.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gearlistapp.R
import com.example.gearlistapp.presentation.screen.gear.stringToImageVector
import com.example.gearlistapp.presentation.viewmodel.CategoryListState
import com.example.gearlistapp.presentation.viewmodel.CategoryViewModel
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.LocationViewModel
import com.example.gearlistapp.ui.model.toUiText

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
    gearViewModel: GearViewModel,
    categoryViewModel: CategoryViewModel,
    locationViewModel: LocationViewModel,
    onCategorySelected: (Int) -> Unit,
    previousCategory: String = "null",
    isError: MutableState<Boolean> = mutableStateOf(false),
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
            isError = isError.value,
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
            },
            supportingText = {
                if (isError.value) {
                    Text(
                        text = stringResource(id = R.string.this_field_is_required),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
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
                                        isError.value = false
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