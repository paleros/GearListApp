package com.example.gearlistapp.presentation.screen.category

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gearlistapp.R
import com.example.gearlistapp.data.entities.CategoryEntity
import com.example.gearlistapp.presentation.screen.gear.stringToImageVector
import com.example.gearlistapp.presentation.dialog.DeleteConfirmationDialog

/**
 * Kategoria elem
 * @param category a kategoria
 * @param onDelete a kategoria torlese
 */
@Composable
fun CategoryItem(category: CategoryEntity,
                 onDelete: (Int) -> Unit) {

    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(category.color))
    ) {
        Row(
            modifier = Modifier.padding(5.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = stringToImageVector(category.iconName),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.run { size(20.dp) }
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = category.name, fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White)
            Spacer(modifier = Modifier.width(5.dp).weight(1f))
            IconButton(
                onClick = { showDialog = true },

            ) {
                Icon(Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White)
            }
        }
    }
    if (showDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                onDelete(category.id)
                showDialog = false
                Toast.makeText(context, R.string.delete_successful , Toast.LENGTH_LONG).show()
            },
            onDismiss = { showDialog = false }
        )
    }
}

/**
 * Preview a CategoryItem-hez
 */
@Preview
@Composable
fun CategoryItemPreview() {
    CategoryItem(
        category = CategoryEntity(
            id = 1,
            name = "Category",
            iconName = "Icons.Default.Star",
            color = Color.Red.toArgb()
        ),
        onDelete = {}
    )
}