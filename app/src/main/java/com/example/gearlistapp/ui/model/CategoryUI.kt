package com.example.gearlistapp.ui.model

import androidx.compose.ui.graphics.Color
import com.example.gearlistapp.R
import com.example.gearlistapp.data.model.Category

data class CategoryUi(
    val id: Int = 0,
    val name: String = "",
    val iconRes: Int = R.drawable.ic_launcher_foreground,
    val color: Int = Color.Gray.value.toInt()
)

fun Category.asCategoryUi(): CategoryUi = CategoryUi(
    id = id,
    name = name,
    iconRes = iconRes,
    color = color
)

fun CategoryUi.asCategory(): Category = Category(
    id = id,
    name = name,
    iconRes = iconRes,
    color = color
)