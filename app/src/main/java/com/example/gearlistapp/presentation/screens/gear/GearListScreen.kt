package com.example.gearlistapp.presentation.screens.gear

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.TextField
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import com.example.gearlistapp.presentation.dialogs.CategoryAndLocationFilterDialog
import com.example.gearlistapp.presentation.dialogs.gear.GearCreateDialog
import com.example.gearlistapp.presentation.dialogs.gear.GearDetailDialog
import com.example.gearlistapp.presentation.viewmodel.CategoryViewModel
import com.example.gearlistapp.presentation.viewmodel.LocationViewModel
import com.example.gearlistapp.ui.model.GearUi
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
    gearViewModel: GearViewModel,
    categoryViewModel: CategoryViewModel,
    locationViewModel: LocationViewModel
) {


    val gearList = gearViewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    var showCreateDialog by remember { mutableStateOf(false) }
    var showIndicator by remember { mutableStateOf(false) }
    var selectedGear by remember { mutableStateOf<GearUi?>(null) }

    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String>("null") }
    var selectedLocation by remember { mutableStateOf<String>("null") }
    var sortOrder by remember { mutableStateOf(SortOrder.NameAsc) }
    var showFilterDialog by remember { mutableStateOf(false) }

    var flipped by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

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

    showIndicator = "null" != selectedCategory || "null" != selectedLocation

    Scaffold(
        modifier = Modifier.fillMaxSize().padding(0.dp),
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = { showCreateDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                /** Kereses */
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { R.string.search },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp)),
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
                )
                Spacer(modifier = Modifier.width(8.dp))
                /** Filter ikon */
                IconButton(onClick = { showFilterDialog = true }) {
                    Box {
                        Icon(Icons.Default.FilterList, contentDescription = "Filter")
                        if (showIndicator) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .align(Alignment.TopEnd)
                                    .offset(x = 2.dp, y = (-2).dp)
                                    .background(
                                        MaterialTheme.colorScheme.tertiary,
                                        shape = CircleShape
                                    )
                            )
                        }
                    }
                }
                /** Renderzes ikon */
                IconButton(onClick = {
                    flipped = !flipped
                    sortOrder = if
                        (sortOrder == SortOrder.NameAsc) SortOrder.NameDesc
                    else
                        SortOrder.NameAsc
                }) {
                    val rotation by animateFloatAsState(
                        targetValue = if (flipped) 180f else 0f,
                        animationSpec = tween(durationMillis = 300)
                    )

                    Icon(
                        Icons.AutoMirrored.Filled.Sort,
                        contentDescription = "Sort",
                        modifier = Modifier
                            .graphicsLayer { rotationZ = rotation })
                }
            }

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
                                    items(
                                        filteredAndSortedGearList,
                                        key = { gear -> gear.id }) { gear ->
                                        if (gear.parent == -1) {
                                            GearItem(
                                                gear.asGearEntity(),
                                                onClick = { selectedGear = gear },
                                                categoryViewModel = categoryViewModel,
                                                locationViewModel = locationViewModel,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showCreateDialog) {
        GearCreateDialog(
            categoryViewModel = categoryViewModel,
            gearViewModel = gearViewModel,
            locationViewModel = locationViewModel,
            onDismiss = { showCreateDialog = false },
            onSave = { name, description, categoryId, locationId ->
                coroutineScope.launch {
                    gearViewModel.add(name, description, categoryId, locationId){}
                    showCreateDialog = false
                }
            }
        )
    }

    if (showFilterDialog) {
        CategoryAndLocationFilterDialog(
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
            gearId = gear.id,
            onDismiss = { selectedGear = null },
            onDelete = { id ->
                gearViewModel.delete(id)
                selectedGear = null
            },
            onEdit = { id, name, description, categoryId, locationId ->
                val newGear = GearUi(id, name, description, categoryId, locationId)
                gearViewModel.update(newGear)
            },
            gearViewModel = gearViewModel,
            categoryViewModel = categoryViewModel,
            locationViewModel = locationViewModel,
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
