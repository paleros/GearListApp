package com.example.gearlistapp.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Spacer
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

/**
 * A felszerelesek valaszto komponens
 * @param gearViewModel a felszerelesek viewmodelje
 * @param selectedGearIds a valasztott felszerelesek id-jei
 * @param onSelectionChanged a valasztas megvaltozasa
 */
@Composable
fun GearSelector(
    gearViewModel: GearViewModel = viewModel(factory = GearViewModel.Factory),
    selectedGearIds: List<Int>,
    onSelectionChanged: (List<Int>) -> Unit
) {
    val gearList = gearViewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    val selectedIds = remember { mutableStateListOf<Int>().apply { addAll(selectedGearIds) } }

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
                    Text(
                        text = stringResource(id = R.string.select_gears),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 200.dp)
                    ) {
                        items(gearList, key = { it.id }) { gear ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        if (selectedIds.contains(gear.id)) {
                                            selectedIds.remove(gear.id)
                                        } else {
                                            selectedIds.add(gear.id)
                                        }
                                        onSelectionChanged(selectedIds.toList())
                                    }
                                    .padding(vertical = 4.dp, horizontal = 8.dp)
                            ) {
                                Checkbox(
                                    checked = selectedIds.contains(gear.id),
                                    onCheckedChange = {
                                        if (it) selectedIds.add(gear.id) else selectedIds.remove(gear.id)
                                        onSelectionChanged(selectedIds.toList())
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = gear.name)
                            }
                        }
                    }
                }
            }
        }
    }
}
