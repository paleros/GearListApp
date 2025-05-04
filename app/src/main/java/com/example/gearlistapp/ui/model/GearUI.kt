package com.example.gearlistapp.ui.model

import com.example.gearlistapp.data.entities.GearEntity
import com.example.gearlistapp.data.model.Gear
import kotlin.Int
import kotlin.String

/**
 * A felszerelest reprezentalo osztaly a UI szamara.
 */
data class GearUi(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val categoryId: Int = 0,
    val locationId: Int = 0,
    val inPackage: Boolean = false,
    val pieces: Int = 1,
    val parent: Int = -1,
)

/**
 * A felszereles entitasbol konvertalhato vissza a felszereles modell.
 * @receiver a felszereles entitas.
 * @return a felszereles modell.
 */
fun Gear.asGearUi(): GearUi = GearUi(
    id = id,
    name = name,
    description = description,
    categoryId = categoryId,
    locationId = locationId,
    inPackage = inPackage,
    pieces = pieces,
    parent = parent
)

/**
 * A felszereles modellbol konvertalhato vissza a felszereles entitas.
 * @receiver a felszereles modell.
 * @return a felszereles entitas.
 */
fun GearUi.asGear(): Gear = Gear(
    id = id,
    name = name,
    description = description,
    categoryId = categoryId,
    locationId = locationId,
    inPackage = inPackage,
    pieces = pieces,
    parent = parent
)

/**
 * A felszereles entitasbol konvertalhato vissza a felszereles ui.
 * @receiver a felszereles entitas.
 * @return a felszereles ui.
 */
fun GearUi.asGearEntity(): GearEntity = GearEntity(
    id = id,
    name = name,
    description = description,
    categoryId = categoryId,
    locationId = locationId,
    inPackage = inPackage,
    pieces = pieces,
    parent = parent
)