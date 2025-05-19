package com.example.gearlistapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.gearlistapp.R
import com.example.gearlistapp.presentation.viewmodel.GearListState
import com.example.gearlistapp.ui.model.toUiText
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.text.input.KeyboardType
import com.example.gearlistapp.presentation.dialogs.CategoryFilterDialog

/**
 * A felszerelesek valaszto komponens
 * @param gearViewModel a felszerelesek viewmodelje
 * @param selectedMap a valasztott felszerelesek id-jei
 * @param piecesMap a valasztott darabszamok
 */
@Composable
fun GearSelector(
    gearViewModel: GearViewModel = viewModel(factory = GearViewModel.Factory),
    piecesMap: SnapshotStateMap<Int, String>,
    selectedMap: SnapshotStateMap<Int, Boolean>,
) {
    val gearList = gearViewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    var selectedCategory by remember { mutableStateOf<String>("null") }
    var showFilterDialog by remember { mutableStateOf(false) }
    var showIndicator by remember { mutableStateOf(false) }

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

    /** Szures, rendezes es kereses */
    val filteredAndSortedGearList = gearList.let {
        when (it) {
            is GearListState.Result -> {
                it.gearList
                    .filter { gear ->
                        (gear.categoryId.toString() == selectedCategory || "null" == selectedCategory)

                    }
            }
            else -> emptyList()
        }
    }

    showIndicator = "null" != selectedCategory

    when (gearList) {
        is GearListState.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.secondaryContainer
            )
        }

        is GearListState.Error -> {
            Text(
                text = gearList.error.toUiText().asString(context)
            )
        }

        is GearListState.Result -> {
            val gearList = gearList.gearList

            if (gearList.isEmpty()) {
                Text(text = stringResource(id = R.string.text_empty_gear_list))
            } else {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.select_gears),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
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
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 200.dp)
                    ) {
                        items(filteredAndSortedGearList, key = { it.id }) { gear ->

                            if (gear.parent == -1) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp, horizontal = 8.dp)
                                ) {
                                    Checkbox(
                                        checked = selectedMap[gear.id] == true,
                                        onCheckedChange = {
                                            selectedMap[gear.id] = it
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = gear.name)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    TextField(
                                        value = piecesMap[gear.id].toString(),
                                        onValueChange = { newValue ->
                                            piecesMap[gear.id] = newValue.toString()
                                        },
                                        label = { Text(stringResource(id = R.string.pieces)) },
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions.Default.copy(
                                            keyboardType = KeyboardType.Number
                                        ),
                                        )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showFilterDialog) {
        CategoryFilterDialog(
            gearViewModel = gearViewModel,
            onDismiss = { showFilterDialog = false },
            onCategorySelected = { selectedCategory = it.toString() },
            onDeleteFilters = {
                selectedCategory = "null"
                showFilterDialog = false
            },
            previousCategory = selectedCategory,
        )
    }
}
