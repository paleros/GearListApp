package com.example.gearlistapp.data

import androidx.room.TypeConverter

/**
 * A konverterek osztalya.
 */
class Converters {
    /**
     * Az intek listajat konvertalo fuggveny.
     * @param list a lista.
     * @return a stringek listaja.
     */
    @TypeConverter
    fun fromIntList(list: List<Int>?): String {
        return list?.joinToString(",") ?: ""
    }

    /**
     * A stringek listajat konvertalo fuggveny.
     * @param data a string.
     * @return a lista.
     */
    @TypeConverter
    fun toIntList(data: String): List<Int> {
        return if (data.isBlank()) {
            emptyList()
        } else {
            data.split(",").mapNotNull { it.toIntOrNull() }
        }
    }
}