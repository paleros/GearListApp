package com.example.gearlistapp.data.model

import com.example.gearlistapp.data.entities.LocationEntity

/**
 * A helyszint reprezentalo osztaly.
 * @property id a helyszin azonositoja.
 * @property name a helyszin neve.
 */
data class Location(
    override val id: Int,
    override val name: String
) : BaseModel(id, name){

    /**
     * A helyszin modellbol konvertalhato vissza a helyszin entitas.
     * @receiver a helyszin modell.
     * @return a helyszin entitas.
     */
    override fun asEntity(): LocationEntity = LocationEntity(
        id = id,
        name = name,
    )
}