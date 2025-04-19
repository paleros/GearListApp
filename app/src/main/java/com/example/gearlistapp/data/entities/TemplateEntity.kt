package com.example.gearlistapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gearlistapp.data.model.Template
import kotlin.String

/**
 * A sablon entitas, amely a sablonok adatait tarolja.
 * @property id a sablon azonositoja.
 * @property title a sablon neve.
 * @property description a sablon leirasa.
 * @property duration az esemeny idotartama (hany napos?).
 * @property itemList a sablonhoz tartozo elemek listaja.
 * @property backgroundColor a sablon hatter szine.
 */
@Entity(tableName = "template_table")
data class TemplateEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val duration: Int,
    val itemList: List<Int>,
    val backgroundColor: Int
){

    /**
     * A sablon entitasbol konvertalhato vissza a sablon modell.
     * @receiver a sablon entitas.
     * @return a sablon modell.
     */
    fun asBaseModel(): Template = Template(
        id = id,
        title = title,
        description = description,
        duration = duration,
        itemList = itemList,
        backgroundColor = backgroundColor
    )
}