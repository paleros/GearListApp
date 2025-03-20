package com.example.gearlistapp.data.entities

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gearlistapp.data.model.Category

/**
 * A felszereles kategoriakat reprezentalo osztalya.
 * @property id a kategoria azonositoja.
 * @property name a kategoria neve.
 * @property iconRes a kategoria ikonja.
 * @property color a kategoria szine.
 */
@Entity(tableName = "category_table")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String,
    @DrawableRes var iconRes: Int,
    var color: Int
)

/**
 * A kategoria entitasbol konvertalhato vissza a kategoria modell.
 * @receiver a kategoria entitas.
 * @return a kategoria modell.
 */
fun CategoryEntity.asCategory(): Category = Category(
    id = id,
    name = name,
    iconRes = iconRes,
    color = color,
)

/**
 * A kategoria modellbol konvertalhato vissza a kategoria entitas.
 * @receiver a kategoria modell.
 * @return a kategoria entitas.
 */
fun Category.asEntity(): CategoryEntity = CategoryEntity(
    id = id,
    name = name,
    iconRes = iconRes,
    color = color,
)