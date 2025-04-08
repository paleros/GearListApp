package com.example.gearlistapp.presentation.screens.gear

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.gearlistapp.ui.model.asGearEntity
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import com.example.gearlistapp.presentation.dialogs.FilterDialog
import com.example.gearlistapp.presentation.dialogs.gear.GearCreateDialog
import com.example.gearlistapp.presentation.dialogs.gear.GearDetailDialog
import com.example.gearlistapp.presentation.viewmodel.CategoryViewModel
import com.example.gearlistapp.presentation.viewmodel.LocationViewModel
import com.example.gearlistapp.ui.model.GearUi
import com.example.gearlistapp.ui.model.asGear
import kotlinx.coroutines.launch

/**
 * A felszereles lista megjelenitese
 * @param gearViewModel a felszereles viewmodelje
 * @param categoryViewModel a kategoria viewmodelje
 * @param locationViewModel a helyszin viewmodelje
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GearListScreen(
    gearViewModel: GearViewModel = viewModel(factory = GearViewModel.Factory),
    categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.Factory),
    locationViewModel: LocationViewModel = viewModel(factory = LocationViewModel.Factory)
) {


    val gearList = gearViewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedGear by remember { mutableStateOf<GearUi?>(null) }

    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String>("null") }
    var selectedLocation by remember { mutableStateOf<String>("null") }
    var sortOrder by remember { mutableStateOf(SortOrder.NameAsc) }
    var showSearchDialog by remember { mutableStateOf(false) }
    var showFilterDialog by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                gearViewModel.loadGears()
                categoryViewModel.loadCategories()
                locationViewModel.loadLocations()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    /** Szures, rendezes es kereses */
    val filteredAndSortedGearList = gearList.let {
        when (it) {
            is GearListState.Result -> {
                it.gearList
                    .filter { gear ->
                        gear.name.contains(searchText, ignoreCase = true) &&
                                (gear.categoryId.toString() == selectedCategory || "null" == selectedCategory) &&
                                (gear.locationId.toString()== selectedLocation || "null" == selectedLocation)
                    }
                    .sortedWith { gear1, gear2 ->
                        when (sortOrder) {
                            SortOrder.NameAsc -> gear1.name.compareTo(gear2.name)
                            SortOrder.NameDesc -> gear2.name.compareTo(gear1.name)
                        }
                    }
            }
            else -> emptyList()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { "" },
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
                    .fillMaxHeight(0.1f),
                actions = {
                    /** Kereses ikon */
                    IconButton(onClick = { showSearchDialog = true }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    /** Filter ikon */
                    IconButton(onClick = { showFilterDialog = true }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filter")
                    }
                    /** Renderzes ikon */
                    IconButton(onClick = {
                        sortOrder = if
                                (sortOrder == SortOrder.NameAsc) SortOrder.NameDesc
                        else
                            SortOrder.NameAsc
                    }) {
                        Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = "Sort")
                    }
                }
            )
        },
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
                    if (filteredAndSortedGearList.isEmpty()) {
                        Text(text = stringResource(id = R.string.text_empty_gear_list))
                    } else {
                        Column {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                items(filteredAndSortedGearList, key = { gear -> gear.id }) { gear ->
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
            categoryViewModel = categoryViewModel,
            gearViewModel = gearViewModel,
            locationViewModel = locationViewModel,
            onDismiss = { showAddDialog = false },
            onSave = { name, description, categoryId, locationId ->
                coroutineScope.launch {
                    gearViewModel.add(name, description, categoryId, locationId)
                    showAddDialog = false
                }
            }
        )
    }

    if (showFilterDialog) {
        FilterDialog(
            categoryViewModel = categoryViewModel,
            gearViewModel = gearViewModel,
            locationViewModel = locationViewModel,
            onDismiss = { showFilterDialog = false },
            onCategorySelected = { selectedCategory = it.toString() },
            onLocationSelected = { selectedLocation = it.toString() },
            onDeleteFilters = {
                selectedCategory = "null"
                selectedLocation = "null"
                showFilterDialog = false
            },
            previousCategory = selectedCategory,
            previousLocation = selectedLocation,
        )
    }

    selectedGear?.let {gear ->
        GearDetailDialog(
            gear = gear.asGear(),
            onDismiss = { selectedGear = null },
            onDelete = { id ->
                gearViewModel.delete(id)
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
 * A felszerelesek rendezesi iranya
 */
enum class SortOrder {
    NameAsc,
    NameDesc
}
