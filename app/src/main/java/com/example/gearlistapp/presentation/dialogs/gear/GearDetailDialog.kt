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
import androidx.compose.ui.tooling.preview.Preview
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
 * @param gear a felszereles
 * @param onDismiss a dialogus bezarasa
 * @param onDelete a felszereles torlese
 * @param onEdit a felszereles szerkesztese
 */
@Composable
fun GearDetailDialog(
    gear: Gear,
    gearViewModel: GearViewModel = viewModel(factory = GearViewModel.Factory),
    categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.Factory),
    locationViewModel: LocationViewModel = viewModel(factory = LocationViewModel.Factory),
    onDismiss: () -> Unit,
    onDelete: (Int) -> Unit,
    onEdit: (Gear) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    var category by remember { mutableStateOf<CategoryEntity?>(null) }
    var location by remember { mutableStateOf<LocationEntity?>(null) }
    var locationName by remember { mutableStateOf("") }

    LaunchedEffect(gear.categoryId) {
        category = categoryViewModel.getById(gear.categoryId)?.asCategory()?.asEntity()
    }
    LaunchedEffect(gear.locationId) {
        location = locationViewModel.getById(gear.locationId)?.asLocation()?.asEntity()
        locationName = location?.name ?: ""
    }

    val categoryColor = Color(category?.color ?: -7829368)
    val categoryName = category?.name ?: ""
    val categoryIcon = category?.iconName ?: "Icons.Default.Star"

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = gear.name, fontSize = 20.sp, fontWeight = FontWeight.Bold) },
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
                Text(text = gear.description)

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
                    onClick = { showDialog = true },
                ) {
                    Icon(Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.onPrimary)
                }
                IconButton(onClick = { onEdit(gear) }) {
                    Icon(Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    )

    if (showDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                onDelete(gear.id)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}

/**
 * A felszereles reszletezo dialogus elozetes megjelenitese
 */
@Composable
@Preview
fun GearDetailDialogPreview() {
    GearDetailDialog(
        gear = Gear(1, "Test Gear",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus vel leo quis libero mattis molestie.",
            1, 1),
        onDismiss = {},
        onDelete = {},
        onEdit = {}
    )
}
