package com.example.gearlistapp.data.repository

import com.example.gearlistapp.data.entities.CategoryEntity
import com.example.gearlistapp.data.model.Category
import kotlinx.coroutines.flow.Flow

/**
 * A Repository interfesz, amely a CRUD muveleteket tartalmazza.
 */
interface CategoryRepository{
    fun getAll() : Flow<List<CategoryEntity>>
    fun getById(id: Int) : Flow<CategoryEntity>
    suspend fun insert(category: CategoryEntity)
    suspend fun update(category: CategoryEntity)
    suspend fun delete(category: CategoryEntity)
    suspend fun deleteById(id: Int)
}