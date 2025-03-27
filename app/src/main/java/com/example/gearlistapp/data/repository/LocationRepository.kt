package com.example.gearlistapp.data.repository

import com.example.gearlistapp.data.entities.LocationEntity
import kotlinx.coroutines.flow.Flow

/**
 * A Repository interfesz, amely a CRUD muveleteket tartalmazza.
 */
interface LocationRepository{
    fun getAll() : Flow<List<LocationEntity>>
    fun getById(id: Int) : Flow<LocationEntity>
    suspend fun insert(entity: LocationEntity)
    suspend fun update(entity: LocationEntity)
    suspend fun delete(entity: LocationEntity)
    suspend fun deleteById(id: Int)
}