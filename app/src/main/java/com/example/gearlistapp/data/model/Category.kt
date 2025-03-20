package com.example.gearlistapp.data.model

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
)