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
    @PrimaryKey(autoGenerate = true) override var id: Int = 0,
    override var name: String,
    @DrawableRes var iconRes: Int,
    var color: Int
) : BaseEntity(id, name){

    /**
     * A kategoria entitasbol konvertalhato vissza a kategoria modell.
     * @receiver a kategoria entitas.
     * @return a kategoria modell.
     */
    override fun asBaseModel(): Category = Category(
        id = id,
        name = name,
        iconRes = iconRes,
        color = color,
    )
}