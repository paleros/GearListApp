package com.example.gearlistapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gearlistapp.data.model.Location

/**
 * A helyszineket reprezentalo osztaly.
 * @property id a helyszin azonositoja.
 * @property name a helyszin neve.
 */
@Entity(tableName = "location_table")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)

/**
 * A helyszin entitasbol konvertalhato vissza a helyszin modell.
 * @receiver a helyszin entitas.
 * @return a helyszin modell.
 */
fun LocationEntity.asLocation(): Location = Location(
    id = id,
    name = name,
)

/**
 * A helyszin modellbol konvertalhato vissza a helyszin entitas.
 * @receiver a helyszin modell.
 * @return a helyszin entitas.
 */
fun Location.asEntity(): LocationEntity = LocationEntity(
    id = id,
    name = name,
)