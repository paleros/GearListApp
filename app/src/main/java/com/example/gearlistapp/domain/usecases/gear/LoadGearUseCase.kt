package com.example.gearlistapp.domain.usecases.gear

import com.example.gearlistapp.data.repository.GearRepository
import com.example.gearlistapp.data.model.Gear
import kotlinx.coroutines.flow.first
import java.io.IOException

/**
 * A betolteset vegzo osztaly.
 * @property repository a taroloja.
 */
class LoadGearUseCase(private val repository: GearRepository) {

    /**
     * Az elem betoltese.
     * @return az elemek listaja.
     */
    suspend operator fun invoke(): Result<List<Gear>> {
        return try {
            val entities = repository.getAll().first()
            Result.success(entities.map { it.asBaseModel() })
        } catch (e: IOException) {
            Result.failure(e)
        }
    }
}