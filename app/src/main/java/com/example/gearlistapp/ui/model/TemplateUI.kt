package com.example.gearlistapp.ui.model

import com.example.gearlistapp.data.entities.TemplateEntity
import com.example.gearlistapp.data.model.Template
import kotlin.Int
import kotlin.String

/**
 * A sablon reprezentalo osztaly a UI szamara.
 */
data class TemplateUi(
    val id: Int = 0,
    val title: String,
    val description: String,
    val duration: Int,
    val itemList: List<Int>,
    val backgroundColor: Int
)

/**
 * A sablon entitasbol konvertalhato vissza a sablon modell.
 * @receiver a sablon entitas.
 * @return a sablon modell.
 */
fun Template.asTemplateUi(): TemplateUi = TemplateUi(
    id = id,
    title = title,
    description = description,
    duration = duration,
    itemList = itemList,
    backgroundColor = backgroundColor
)

/**
 * A sablon modellbol konvertalhato vissza a sablon entitas.
 * @receiver a sablon modell.
 * @return a sablon entitas.
 */
fun TemplateUi.asTemplate(): Template = Template(
    id = id,
    title = title,
    description = description,
    duration = duration,
    itemList = itemList,
    backgroundColor = backgroundColor
)

/**
 * A sablon entitasbol konvertalhato vissza a sablon ui.
 * @receiver a sablon entitas.
 * @return a sablon ui.
 */
fun TemplateUi.asTemplateEntity(): TemplateEntity = TemplateEntity(
    id = id,
    title = title,
    description = description,
    duration = duration,
    itemList = itemList,
    backgroundColor = backgroundColor
)