package com.example.gearlistapp.presentation.screens.gear

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.ElectricalServices
import androidx.compose.material.icons.filled.Festival
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gearlistapp.data.entities.CategoryEntity
import com.example.gearlistapp.data.entities.GearEntity
import com.example.gearlistapp.data.entities.LocationEntity
import com.example.gearlistapp.presentation.viewmodel.CategoryViewModel
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.LocationViewModel
import com.example.gearlistapp.ui.model.asCategory
import com.example.gearlistapp.ui.model.asLocation

/**
 * Egy felszereles elem megjelenitese
 * @param gear a felszereles entitas
 * @param viewModel a felszereles viewmodelje
 * @param onClick a kattintas esemeny
 */
@Composable
fun GearItem(gear: GearEntity,
             categoryViewModel: CategoryViewModel,
             locationViewModel: LocationViewModel,
             onClick: () -> Unit) {

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
    val categoryIcon = category?.iconName ?: "Icons.Default.Star"


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {onClick()},
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = categoryColor)
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = stringToImageVector(categoryIcon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.run { size(20.dp) }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = gear.name, fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White)
            Spacer(modifier = Modifier.width(16.dp).weight(1f))
            Text(text = locationName,
                fontSize = 14.sp,
                color = Color.White)
        }
    }
}

/**
 * Stringbol ImageVector-t keszit
 * @param iconName az ikon neve
 * @return az ikon ImageVector-e
 */
fun stringToImageVector(iconName: String): ImageVector {
    return when (iconName) {
        "Icons.Default.Star" -> Icons.Default.Star
        "Icons.Default.Festival" -> Icons.Default.Festival
        "Icons.Default.Backpack" -> Icons.Default.Backpack
        "Icons.Default.ElectricalServices" -> Icons.Default.ElectricalServices
        "Icons.Default.Checkroom" -> Icons.Default.Checkroom
        "Icons.Default.WaterDrop" -> Icons.Default.WaterDrop
        "Icons.Default.RocketLaunch" -> Icons.Default.RocketLaunch
        "Icons.Default.Cookie" -> Icons.Default.Cookie
        else -> Icons.Default.Star
    }
}