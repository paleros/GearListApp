package com.example.gearlistapp.presentation.dialogs.actualtemplate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gearlistapp.R
import com.example.gearlistapp.data.model.Template
import com.example.gearlistapp.presentation.dialogs.LocationFilterDialog
import com.example.gearlistapp.presentation.dialogs.template.TemplateEditDialog
import com.example.gearlistapp.presentation.screens.actualtemplate.formatDate
import com.example.gearlistapp.presentation.screens.actualtemplate.isToday
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.TemplateViewModel
import com.example.gearlistapp.ui.common.DeleteConfirmationDialog

/**
 * Az aktuális sablon adatait megjelenito dialogus
 * @param templateId a sablon azonositoja
 * @param gearViewModel a felszereles viewmodelje
 * @param templateViewModel a sablon viewmodelje
 * @param onDismiss a dialogus bezarasa
 * @param onDelete a sablon torlese
 * @param onEdit a sablon modositasa
 */
@Composable
fun ActualTemplateDetailDialog(
    templateId: Int,
    gearViewModel: GearViewModel = viewModel(factory = GearViewModel.Factory),
    templateViewModel: TemplateViewModel = viewModel(factory = TemplateViewModel.Factory),
    onDismiss: () -> Unit,
    onDelete: (Int) -> Unit,
    onEdit: (Int, String, String, Int, SnapshotStateMap<Int, Boolean>, SnapshotStateMap<Int, String>, Int, String) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var showReadyDialog by remember { mutableStateOf(false) }

    var template by remember {mutableStateOf<Template?>(null)}
    var templateGearList by remember { mutableStateOf<List<Int>>(emptyList()) }

    var selectedLocation by remember { mutableStateOf<String>("null") }
    var showFilterDialog by remember { mutableStateOf(false) }
    var showIndicator by remember { mutableStateOf(false) }

    fun refreshTemplate() {
        templateViewModel.getById(templateId) { result ->
            if (result == null) {
                onDismiss()
            }
            template = result
        }
    }
    LaunchedEffect(Unit) {
        refreshTemplate()
    }
    LaunchedEffect(template?.itemList) {
        templateGearList = template?.itemList?: emptyList()
    }

    val backgroundColor = Color(template?.backgroundColor ?: Color.Gray.toArgb())
    val darkerBackgroundColor = backgroundColor.copy(
        red = backgroundColor.red * 0.8f,
        green = backgroundColor.green * 0.8f,
        blue = backgroundColor.blue * 0.8f
    )

    showIndicator = "null" != selectedLocation

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = backgroundColor,
        title = {
            Text(
                text = template?.title ?: "",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            Column(modifier = Modifier.padding(16.dp)) {
                Row {
                    Box(
                        modifier = Modifier
                            .background(darkerBackgroundColor, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "${template?.duration} " +
                                    stringResource(R.string.day),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .background(darkerBackgroundColor, shape = RoundedCornerShape(8.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = formatDate(template?.date ?: "1999-01-01"),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (isToday(template?.date ?: "1999-01-01")) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.error,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Warning",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = stringResource(R.string.it_s_today),
                                fontSize = 15.sp,
                                color = Color.White
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.description) + ":",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = template?.description ?: "")

                Spacer(modifier = Modifier.height(8.dp))
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
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                ) {
                    items(templateGearList) { item ->

                        var itemName by remember { mutableStateOf("") }
                        var itemPieces by remember { mutableStateOf("") }
                        var itemLocationId by remember { mutableStateOf("") }
                        var itemChecked by remember { mutableStateOf(false) }
                        gearViewModel.getById(id = item) { itemName = it?.name.toString() }
                        gearViewModel.getById(id = item) { itemPieces = it?.pieces.toString() }
                        gearViewModel.getById(id = item) { itemChecked = it?.inPackage == true }
                        gearViewModel.getById(id = item) { itemLocationId = it?.locationId.toString() }
                        if (itemLocationId == selectedLocation
                            || "null" == selectedLocation) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 1.dp)
                            ) {
                                Checkbox(
                                    checked = itemChecked,
                                    onCheckedChange = { isChecked ->
                                        itemChecked = isChecked
                                        gearViewModel.updateInPackage(item, isChecked)
                                        gearViewModel.checkIfAllInPackage(templateGearList) { allChecked ->
                                            if (allChecked == 100) {
                                                showReadyDialog = true
                                            }
                                        }
                                    }
                                )

                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = itemName,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "$itemPieces ${stringResource(R.string.pcs)}",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                IconButton(
                    onClick = { showDeleteDialog = true },
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                IconButton(onClick = { showEditDialog = true }) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    )

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                onDelete(template?.id ?: -1)
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    if (showEditDialog) {
        TemplateEditDialog(
            templateId = template?.id ?: -1,
            templateViewModel = templateViewModel,
            onDismiss = { showEditDialog = false },
            onEdit = { id, title, description, duration, selectedMap, piecesMap, backgroundColor, date ->
                onEdit(id, title, description, duration, selectedMap, piecesMap, backgroundColor, date)
                showEditDialog = false
                refreshTemplate()
            }
        )
    }

    if (showReadyDialog) {
        AllInPackageDialog(
            onDismiss = { showReadyDialog = false
                templateGearList.forEach { gear ->
                    gearViewModel.updateInPackage(gear, false)
                }
                templateViewModel.delete(templateId)
                onDismiss()
            }
        )
    }

    if (showFilterDialog) {
        LocationFilterDialog(
            gearViewModel = gearViewModel,
            onDismiss = { showFilterDialog = false },
            onLocationSelected = { selectedLocation = it.toString()
                refreshTemplate() },
            onDeleteFilters = {
                selectedLocation = "null"
                showFilterDialog = false
            },
            previousLocation = selectedLocation,
        )
    }
}