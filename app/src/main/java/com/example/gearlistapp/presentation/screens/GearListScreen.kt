package com.example.gearlistapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gearlistapp.R
import com.example.gearlistapp.presentation.viewmodel.GearListState
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.ui.model.toUiText
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.gearlistapp.GearApplication.Companion.gearRepository
import com.example.gearlistapp.data.model.Gear
import com.example.gearlistapp.domain.usecases.gear.GearUseCases
import com.example.gearlistapp.ui.model.asGearEntity
import androidx.compose.material3.Icon
import com.example.gearlistapp.presentation.dialogs.GearCreateDialog
import com.example.gearlistapp.presentation.dialogs.GearDetailDialog
import com.example.gearlistapp.ui.model.GearUi
import com.example.gearlistapp.ui.model.asGear
import kotlinx.coroutines.launch

/**
 * A felszereles lista megjelenitese
 * @param gearViewModel a felszereles viewmodelje
 */
@Composable
fun GearListScreen(
    gearViewModel: GearViewModel = viewModel(factory = GearViewModel.Factory)
) {


    val gearList = gearViewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedGear by remember { mutableStateOf<GearUi?>(null) }

    //TODO automatikus frissitese a lista megjelenesnek
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                gearViewModel.loadGears()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
                .background(
                    color = if (gearList is GearListState.Loading || gearList is GearListState.Error) {
                        MaterialTheme.colorScheme.secondaryContainer
                    } else {
                        MaterialTheme.colorScheme.background
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            when (gearList) {
                is GearListState.Loading -> CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondaryContainer
                )

                is GearListState.Error -> Text(
                    text = gearList.error.toUiText().asString(context)
                )

                is GearListState.Result -> {
                    if (gearList.gearList.isEmpty()) {
                        Text(text = stringResource(id = R.string.text_empty_gear_list))
                    } else {
                        Column {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                items(gearList.gearList, key = { gear -> gear.id }) { gear ->
                                    GearItem(gear.asGearEntity(),
                                        onClick = { selectedGear = gear })
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        GearCreateDialog(
            onDismiss = { showAddDialog = false },
            onSave = { name, description, categoryId, locationId ->
                coroutineScope.launch {
                    saveToDatabase(name, description, categoryId, locationId)
                    showAddDialog = false
                }
            }
        )
    }

    selectedGear?.let {gear ->
        GearDetailDialog(
            gear = gear.asGear(),
            onDismiss = { selectedGear = null },
            onDelete = { id ->
                gearViewModel.deleteGear(id)
                selectedGear = null
            },
            onEdit = { gearToEdit ->
                // TODO szerkesztes funkcio
                selectedGear = null
            }
        )
    }
}

/**
 * Felszereles mentese az adatbazisba
 * @param name a felszereles neve
 * @param description a felszereles leirasa
 * @param categoryId a kategori id
 * @param locationId a helyszin id
 */
suspend fun saveToDatabase(name: String, description: String, categoryId: Int, locationId: Int) {
    val newItem = Gear(
        name = name,
        description = description,
        categoryId = categoryId,
        locationId = locationId,
        id = 0
    )
    val gearOperations = GearUseCases(gearRepository)

    gearOperations.save(newItem)
}

