package com.example.gearlistapp.data.repository

import kotlinx.coroutines.flow.Flow

/**
 * A Repository interfesz, amely a CRUD muveleteket tartalmazza.
 * @param T a tarolt entitas tipusa.
 */
interface Repository<T>{
    fun getAll() : Flow<List<T>>
    fun getById(id: Int) : Flow<T>
    suspend fun insert(t: T)
    suspend fun update(t: T)
    suspend fun delete(t: T)
    suspend fun deleteById(id: Int)
}