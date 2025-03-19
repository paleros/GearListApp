package com.example.gearlistapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A helyszineket reprezentalo osztaly.
 * @property id a helyszin azonositoja.
 * @property name a helyszin neve.
 */
@Entity(tableName = "location_table")
data class Location(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)