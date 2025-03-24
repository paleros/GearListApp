package com.example.gearlistapp.data.entities

import androidx.room.PrimaryKey
import com.example.gearlistapp.data.model.BaseModel
import com.example.gearlistapp.data.model.Location

/**
 * Absztrakt osztaly az adatbazis entitasokhoz.
 * @property id az egyedi azonosito.
 * @property name az entitas neve.
 */
abstract class BaseEntity(
    @PrimaryKey(autoGenerate = true) open val id: Int = 0,
    open val name: String
){
    /**
     * Az entitasbol konvertalhato vissza a modell.
     * @return a modell.
     */
    abstract fun asBaseModel(): BaseModel
}