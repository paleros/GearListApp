package com.example.gearlistapp.ui.model

import com.example.gearlistapp.data.model.Location

/**
 * a helszin UI modellje
 * @param id a helszin id-je
 * @param name a helszin neve
 */
data class LocationUi(
    val id: Int = 0,
    val name: String = "",
)

/**
 * Location modell konvertalasa Location UI modelle
 */
fun Location.asLocationUi(): LocationUi = LocationUi(
    id = id,
    name = name
)

/**
 * Location UI modell konvertalasa Location modellre
 */
fun LocationUi.asLocation(): Location = Location(
    id = id,
    name = name
)