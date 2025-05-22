package com.example.gearlistapp.presentation.dialogs.location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gearlistapp.R
import com.example.gearlistapp.presentation.screens.location.LocationItem
import com.example.gearlistapp.presentation.viewmodel.CategoryViewModel
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.LocationListState
import com.example.gearlistapp.presentation.viewmodel.LocationViewModel
import com.example.gearlistapp.ui.model.asLocation
import com.example.gearlistapp.ui.model.toUiText
import kotlinx.coroutines.launch

/**
 * A helyszin lista dialogus.
 * @param gearViewModel a felszereles viewmodelje
 * @param categoryViewModel a kategoria viewmodelje
 * @param locationViewModel a helyszin viewmodelje
 * @param onDismiss a dialogus bezarasahoz
 */
@Composable
fun LocationListDialog(
    gearViewModel: GearViewModel,
    categoryViewModel: CategoryViewModel,
    locationViewModel: LocationViewModel,
    onDismiss: () -> Unit,
){
    val locationList = locationViewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    var showAddDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

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
        title = { Text(text = stringResource(id = R.string.locations)) },
        text = {
            when (locationList) {
                is LocationListState.Loading -> CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondaryContainer
                )

                is LocationListState.Error -> Text(
                    text = locationList.error.toUiText().asString(context)
                )

                is LocationListState.Result -> {
                    if (locationList.locationList.isEmpty()) {
                        Text(text = stringResource(id = R.string.text_empty_location_list))
                    } else {
                        Column {
                            LazyColumn(
                                modifier = Modifier.fillMaxHeight()
                            ) {
                                items(locationList.locationList, key = { location -> location.id }) { location ->
                                    LocationItem(
                                        location.asLocation().asEntity(),
                                        onDelete = { id ->
                                            locationViewModel.delete(location.id)
                                        },
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            LargeFloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(40.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }

            TextButton(onClick = { onDismiss() }) {
                Text(stringResource(id = R.string.close))
            }
        }
    )

    if (showAddDialog) {
        LocationCreateDialog(
            onDismiss = { showAddDialog = false },
            onSave = { name ->
                coroutineScope.launch {
                    locationViewModel.add(name)
                    showAddDialog = false
                }
            }
        )
    }
}