package com.example.gearlistapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gearlistapp.data.model.Category

/**
 * A felszereles kategoriakat reprezentalo osztalya.
 * @property id a kategoria azonositoja.
 * @property name a kategoria neve.
 * @property iconName a kategoria ikonja.
 * @property color a kategoria szine.
 */
@Entity(tableName = "category_table")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String,
    var iconName: String,
    var color: Int
){

    /**
     * A kategoria entitasbol konvertalhato vissza a kategoria modell.
     * @receiver a kategoria entitas.
     * @return a kategoria modell.
     */
    fun asBaseModel(): Category = Category(
        id = id,
        name = name,
        iconName = iconName,
        color = color,
    )
}