package com.example.gearlistapp.domain.usecases.gear

import com.example.gearlistapp.data.entities.GearEntity
import com.example.gearlistapp.data.entities.asGear
import com.example.gearlistapp.data.model.Gear
import com.example.gearlistapp.data.repository.Repository
import kotlinx.coroutines.flow.first
import java.io.IOException

/**
 * A felszerelesek betolteset vegzo osztaly.
 * @property repository a felszerelesek taroloja.
 */
class LoadGearsUseCase(private val repository: Repository<GearEntity>) {

    /**
     * Felszerelesek betoltese.
     * @return a felszerelesek listaja.
     */
    suspend operator fun invoke(): Result<List<Gear>> {
        return try {
            val todos = repository.getAll().first()
            Result.success(todos.map { it.asGear() })
        } catch (e: IOException) {
            Result.failure(e)
        }
    }
}