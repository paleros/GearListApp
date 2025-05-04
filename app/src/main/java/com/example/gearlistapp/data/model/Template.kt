package com.example.gearlistapp.data.model

import com.example.gearlistapp.data.entities.TemplateEntity
import kotlin.String

/**
 * A sablon modell, amely a sablonok adatait tarolja.
 * @property id a sablon azonositoja.
 * @property title a sablon neve.
 * @property description a sablon leirasa.
 * @property duration az esemeny idotartama (hany napos?).
 * @property itemList a sablonhoz tartozo elemek listaja.
 * @property backgroundColor a sablon hatter szine.
 * @property date a program idopontja, csak akkor lényeges, ha konkret.
 * @property concrete konkret-e a sablon.
 */
data class Template(
    val id: Int,
    val title: String,
    val description: String,
    val duration: Int,
    val itemList: List<Int>,
    val backgroundColor: Int,
    val date: String,
    val concrete: Boolean
){

    /**
     * A sablon modellbol konvertalhato vissza a sablon entitas.
     * @receiver a sablon modell.
     * @return a sablon entitas.
     */
    fun asEntity(): TemplateEntity = TemplateEntity(
        id = id,
        title = title,
        description = description,
        duration = duration,
        itemList = itemList,
        backgroundColor = backgroundColor,
        date = date,
        concrete = concrete
    )
}