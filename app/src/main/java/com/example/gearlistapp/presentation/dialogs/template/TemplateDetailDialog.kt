package com.example.gearlistapp.presentation.dialogs.template

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
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
import com.example.gearlistapp.presentation.viewmodel.CategoryViewModel
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.LocationViewModel
import com.example.gearlistapp.presentation.viewmodel.TemplateViewModel
import com.example.gearlistapp.ui.common.DeleteConfirmationDialog

/**
 * A sablon adatait megjelenito dialogus
 * @param templateId a sablon azonositoja
 * @param gearViewModel a felszereles viewmodelje
 * @param templateViewModel a sablon viewmodelje
 * @param categoryViewModel a kategoria viewmodelje
 * @param locationViewModel a helyszin viewmodelje
 * @param onDismiss a dialogus bezarasa
 * @param onDelete a sablon torlese
 * @param onEdit a sablon modositasa
 */
@Composable
fun TemplateDetailDialog(
    templateId: Int,
    gearViewModel: GearViewModel,
    templateViewModel: TemplateViewModel,
    categoryViewModel: CategoryViewModel,
    locationViewModel: LocationViewModel,
    onDismiss: () -> Unit,
    onDelete: (Int) -> Unit,
    onEdit: (Int, String, String, Int, SnapshotStateMap<Int, Boolean>, SnapshotStateMap<Int, String>, Int) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    var template by remember {mutableStateOf<Template?>(null)}
    var templateGearList by remember { mutableStateOf<List<Int>>(emptyList()) }

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

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = backgroundColor,
        title = {
            Text(
                text = template?.title ?: "",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(modifier = Modifier.padding(16.dp)) {
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
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.description) + ":",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = template?.description ?: "")

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.gear_list) + ":",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                templateGearList.forEach { item ->
                    var itemName by remember { mutableStateOf("") }
                    var itemPieces by remember { mutableStateOf("") }
                    gearViewModel.getById(id = item) { itemName = it?.name.toString() }
                    gearViewModel.getById(id = item) { itemPieces = it?.pieces.toString() }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    Color.DarkGray,
                                    shape = RoundedCornerShape(2.dp)
                                )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = itemName,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.width(30.dp))
                        Text(
                            text = itemPieces + " " + stringResource(R.string.pcs),
                            style = MaterialTheme.typography.bodySmall
                        )
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
                    androidx.compose.material3.Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                IconButton(
                    onClick = { showDeleteDialog = true },
                ) {
                    androidx.compose.material3.Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                IconButton(onClick = { showEditDialog = true }) {
                    androidx.compose.material3.Icon(
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
                onEdit(id, title, description, duration, selectedMap, piecesMap, backgroundColor)
                showEditDialog = false
                refreshTemplate()
            },
            gearViewModel = gearViewModel,
            categoryViewModel = categoryViewModel,
            locationViewModel = locationViewModel,
        )
    }
}
