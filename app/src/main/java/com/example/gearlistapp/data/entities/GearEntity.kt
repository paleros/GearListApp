package com.example.gearlistapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gearlistapp.data.model.Gear

/**
 * A felszereles kategoriakat reprezentalo osztalya.
 * @property id a kategoria azonositoja.
 * @property name a kategoria neve.
 * @property description a kategoria leirasa.
 * @property categoryId a kategoria azonositoja.
 * @property locationId a helyszin azonositoja.
 */
@Entity(tableName = "gear_table")
data class GearEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val categoryId: Int,
    val locationId: Int
)

/**
 * A felszereles entitasbol konvertalhato vissza a felszereles modell.
 * @receiver a felszereles entitas.
 * @return a felszereles modell.
 */
fun GearEntity.asGear(): Gear = Gear(
    id = id,
    name = name,
    description = description,
    categoryId = categoryId,
    locationId = locationId
)

/**
 * A felszereles modellbol konvertalhato vissza a felszereles entitas.
 * @receiver a felszereles modell.
 * @return a felszereles entitas.
 */
fun Gear.asEntity(): GearEntity = GearEntity(
    id = id,
    name = name,
    description = description,
    categoryId = categoryId,
    locationId = locationId
)