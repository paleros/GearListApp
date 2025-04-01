package com.example.gearlistapp.ui.model

import androidx.compose.ui.graphics.Color
import com.example.gearlistapp.data.model.Category

/**
 * A kategoria ui-t reprezentalo osztaly.
 */
data class CategoryUi(
    val id: Int = 0,
    val name: String = "",
    val iconName: String = "Icons.Default.Star",
    val color: Int = Color.Gray.value.toInt()
)

/**
 * A kategoria modellbol konvertalhato vissza a kategoria ui.
 * @receiver a kategoria modell.
 * @return a kategoria ui.
 */
fun Category.asCategoryUi(): CategoryUi = CategoryUi(
    id = id,
    name = name,
    iconName = iconName,
    color = color
)

/**
 * A kategoria ui-bol konvertalhato vissza a kategoria modell.
 * @receiver a kategoria ui.
 * @return a kategoria modell.
 */
fun CategoryUi.asCategory(): Category = Category(
    id = id,
    name = name,
    iconName = iconName,
    color = color
)