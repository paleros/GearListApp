package com.example.gearlistapp.data.model

import com.example.gearlistapp.data.entities.BaseEntity

/**
 * Absztrakt osztaly az adatbazis modelhez.
 * @property id az egyedi azonosito.
 * @property name az entitas neve.
 */
abstract class BaseModel(
    open val id: Int = 0,
    open val name: String
){
    /**
     * A modellbol konvertalhato vissza az entitas.
     * @return az entitas.
     */
    abstract fun asEntity(): BaseEntity
}