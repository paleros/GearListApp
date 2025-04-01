package com.example.gearlistapp.presentation.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gearlistapp.R
import com.example.gearlistapp.data.model.Gear
import com.example.gearlistapp.ui.common.ColoredIconBoxText
import com.example.gearlistapp.ui.common.DeleteConfirmationDialog

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
    onDismiss: () -> Unit,
    onDelete: (Int) -> Unit,
    onEdit: (Gear) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = gear.name, fontSize = 20.sp, fontWeight = FontWeight.Bold) }, //TODO a hatter színe lehetne a category
        text = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(id = R.string.category) + ":",
                    fontSize = 15.sp, fontWeight = FontWeight.Bold)
                ColoredIconBoxText(
                    text = "${gear.categoryId}",
                    icon = Icons.Default.Star,
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    textColor = MaterialTheme.colorScheme.onSecondary)             //TODO megcsinalni a kategóriát

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = stringResource(id = R.string.description) + ":",
                    fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text(text = gear.description)

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = stringResource(id = R.string.location) + ":",
                    fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text(text = "${gear.categoryId}")       //TODO megcsinalni a helyet
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
