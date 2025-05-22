package com.example.gearlistapp.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Room
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gearlistapp.R
import com.example.gearlistapp.presentation.viewmodel.CategoryViewModel
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.LocationListState
import com.example.gearlistapp.presentation.viewmodel.LocationViewModel
import com.example.gearlistapp.ui.model.toUiText

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
    gearViewModel: GearViewModel,
    categoryViewModel: CategoryViewModel,
    locationViewModel: LocationViewModel,
    onLocationSelected: (Int) -> Unit,
    previousLocation: String = "null",
    isError: MutableState<Boolean> = mutableStateOf(false),
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
                                        isError.value = false
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