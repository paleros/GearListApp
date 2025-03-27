package com.example.gearlistapp.data.repository

import com.example.gearlistapp.data.entities.GearEntity
import kotlinx.coroutines.flow.Flow

/**
 * A Repository interfesz, amely a CRUD muveleteket tartalmazza.
 */
interface GearRepository{
    fun getAll() : Flow<List<GearEntity>>
    fun getById(id: Int) : Flow<GearEntity>
    suspend fun insert(gear: GearEntity)
    suspend fun update(gear: GearEntity)
    suspend fun delete(gear: GearEntity)
    suspend fun deleteById(id: Int)
}