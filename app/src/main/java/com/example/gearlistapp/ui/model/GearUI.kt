package com.example.gearlistapp.ui.model

import com.example.gearlistapp.data.entities.GearEntity
import com.example.gearlistapp.data.model.Gear
import kotlin.Int
import kotlin.String

data class GearUi(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val categoryId: Int = 0,
    val locationId: Int = 0,
)

fun Gear.asGearUi(): GearUi = GearUi(
    id = id,
    name = name,
    description = description,
    categoryId = categoryId,
    locationId = locationId,
)

fun GearUi.asGear(): Gear = Gear(
    id = id,
    name = name,
    description = description,
    categoryId = categoryId,
    locationId = locationId
)

fun GearUi.asGearEntity(): GearEntity = GearEntity(
    id = id,
    name = name,
    description = description,
    categoryId = categoryId,
    locationId = locationId
)