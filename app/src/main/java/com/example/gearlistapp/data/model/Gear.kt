package com.example.gearlistapp.data.model

import com.example.gearlistapp.data.entities.GearEntity

/**
 * A felszerelest reprezentalo osztaly.
 * @property id a felszereles azonositoja.
 * @property name a felszereles neve.
 * @property description a felszereles leirasa.
 * @property categoryId a felszereles kategoria azonositoja.
 * @property locationId a felszereles helyszin azonositoja.
 * @property inPackage bepakoltuk-e mar a csomagba, csak akkor lényeges, ha konkret.
 * @property pieces hany darab kell belole, csak akkor lényeges, ha konkret.
 * @property parent konkret-e a felszereles.
 */
data class Gear(
    val id: Int,
    val name: String,
    val description: String,
    val categoryId: Int,
    val locationId: Int,
    var inPackage: Boolean,
    val pieces: Int = 1,
    val parent: Int = -1,
){

    /**
     * A felszereles modellbol konvertalhato vissza a felszereles entitas.
     * @receiver a felszereles modell.
     * @return a felszereles entitas.
     */
    fun asEntity(): GearEntity = GearEntity(
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