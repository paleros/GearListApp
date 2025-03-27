package com.example.gearlistapp.data.model

import com.example.gearlistapp.data.entities.CategoryEntity

/**
 * A kategoriat reprezentalo osztaly.
 * @property id a kategoria azonositoja.
 * @property name a kategoria neve.
 * @property iconRes a kategoria ikonja.
 * @property color a kategoria szine.
 */
data class Category(
    val id: Int,
    val name: String,
    val iconRes: Int,
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
        iconRes = iconRes,
        color = color,
    )
}