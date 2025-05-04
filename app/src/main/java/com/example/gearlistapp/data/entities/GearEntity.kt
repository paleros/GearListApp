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
 * @property inPackage bepakoltuk-e mar a csomagba, csak akkor lenyeges, ha konkret.
 * @property pieces hany darab kell belole, csak akkor lenyeges, ha konkret.
 * @property parent ha van szuloje felszerelesnek, -1 kulonben.
 */
@Entity(tableName = "gear_table")
data class GearEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val categoryId: Int,
    val locationId: Int,
    val inPackage: Boolean,
    val pieces: Int = 1,
    val parent: Int = -1,
){
    /**
     * A felszereles entitasbol konvertalhato vissza a felszereles modell.
     * @receiver a felszereles entitas.
     * @return a felszereles modell.
     */
    fun asBaseModel(): Gear = Gear(
        id = id,
        name = name,
        description = description,
        categoryId = categoryId,
        locationId = locationId,
        inPackage = inPackage,
        pieces = pieces,
        parent = parent
    )
}