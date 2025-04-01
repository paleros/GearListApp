package com.example.gearlistapp.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.ElectricalServices
import androidx.compose.material.icons.filled.Festival
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.gearlistapp.data.entities.CategoryEntity

/**
 * A kategoriat reprezentalo osztaly.
 * @property id a kategoria azonositoja.
 * @property name a kategoria neve.
 * @property iconName a kategoria ikonja.
 * @property color a kategoria szine.
 */
data class Category(
    val id: Int,
    val name: String,
    val iconName: String,
    val color: Int
){

    /**
     * A kategoria modellbol konvertalhato vissza a kategoria entitas.
     * @receiver a kategoria modell.
     * @return a kategoria entitas.
     */
    fun asEntity(): CategoryEntity = CategoryEntity(
        id = id,
        name = name,
        iconName = iconName,
        color = color,
    )
}