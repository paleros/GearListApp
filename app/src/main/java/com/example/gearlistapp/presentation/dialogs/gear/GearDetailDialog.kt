package com.example.gearlistapp.presentation.dialogs.gear

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gearlistapp.R
import com.example.gearlistapp.data.entities.CategoryEntity
import com.example.gearlistapp.data.entities.LocationEntity
import com.example.gearlistapp.data.model.Gear
import com.example.gearlistapp.presentation.screens.gear.stringToImageVector
import com.example.gearlistapp.presentation.viewmodel.CategoryViewModel
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.LocationViewModel
import com.example.gearlistapp.ui.common.ColoredIconBoxText
import com.example.gearlistapp.ui.common.DeleteConfirmationDialog
import com.example.gearlistapp.ui.model.asCategory
import com.example.gearlistapp.ui.model.asLocation

/**
 * A felszereles reszletezo dialogus
 * @param gearId a felszereles azonositoja
 * @param gearViewModel a felszereles viewmodelje
 * @param categoryViewModel a kategoria viewmodelje
 * @param locationViewModel a helyszin viewmodelje
 * @param onDismiss a dialogus bezarasa
 * @param onDelete a felszereles torlese
 * @param onEdit a szerkesztes
 */
@Composable
fun GearDetailDialog(
    gearId: Int,
    gearViewModel: GearViewModel = viewModel(factory = GearViewModel.Factory),
    categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.Factory),
    locationViewModel: LocationViewModel = viewModel(factory = LocationViewModel.Factory),
    onDismiss: () -> Unit,
    onDelete: (Int) -> Unit,
    onEdit: (Int, String, String, Int, Int) -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    var category by remember { mutableStateOf<CategoryEntity?>(null) }
    var location by remember { mutableStateOf<LocationEntity?>(null) }
    var locationName by remember { mutableStateOf("") }

    var gear by remember { mutableStateOf<Gear?>(null) }

    /**
     * A felszereles kiirasanak frissitese
     */
    fun refreshGear() {
        gearViewModel.getById(gearId) { result ->
            if (result == null){
                onDismiss()
            }
            gear = result
        }
    }

    LaunchedEffect(Unit) {
        refreshGear()
    }

    LaunchedEffect(gear?.categoryId) {
        category = categoryViewModel.getById(gear?.categoryId ?: 0)?.asCategory()?.asEntity()
    }
    LaunchedEffect(gear?.locationId) {
        location = locationViewModel.getById(gear?.locationId ?: 0)?.asLocation()?.asEntity()
        locationName = location?.name ?: ""
    }

    val categoryColor = Color(category?.color ?: -7829368)
    val categoryName = category?.name ?: ""
    val categoryIcon = category?.iconName ?: "Icons.Default.Star"

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = gear?.name ?: "", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
        text = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(id = R.string.category) + ":",
                    fontSize = 15.sp, fontWeight = FontWeight.Bold)
                ColoredIconBoxText(
                    text = categoryName,
                    icon = stringToImageVector(categoryIcon),
                    backgroundColor = categoryColor,
                    textColor = Color.White)

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = stringResource(id = R.string.description) + ":",
                    fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text(text = gear?.description ?: "")

                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(text = stringResource(id = R.string.location) + ":",
                        fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = locationName)
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
                        tint = MaterialTheme.colorScheme.onPrimary)
                }
                IconButton(
                    onClick = { showDeleteDialog = true },
                ) {
                    Icon(Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.onPrimary)
                }
                IconButton(onClick = { showEditDialog = true }) {
                    Icon(Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    )

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                onDelete(gear?.id ?: 0)
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    if (showEditDialog) {
        GearEditDialog(
            gearId = gear?.id ?: 0,
            currentName = gear?.name ?: "",
            currentDescription = gear?.description ?: "",
            currentCategoryId = gear?.categoryId ?: 0,
            currentLocationId = gear?.locationId ?: 0,
            categoryViewModel = categoryViewModel,
            gearViewModel = gearViewModel,
            locationViewModel = locationViewModel,
            onDismiss = { showEditDialog = false },
            onEdit = { id, name, description, categoryId, locationId ->
                    onEdit(id, name, description, categoryId, locationId)
                    showEditDialog = false
                    refreshGear()
            }
        )
    }
}

