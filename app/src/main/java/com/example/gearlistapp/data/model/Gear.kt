package com.example.gearlistapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A felszereles kategoriakat reprezentalo osztalya.
 * @property id a kategoria azonositoja.
 * @property name a kategoria neve.
 * @property description a kategoria leirasa.
 * @property categoryId a kategoria azonositoja.
 * @property locationId a helyszin azonositoja.
 */
@Entity(tableName = "gear_table")
data class Gear(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val categoryId: Int,
    val locationId: Int
)