package com.example.gearlistapp.domain.usecases.gear

import com.example.gearlistapp.data.repository.GearRepository
import com.example.gearlistapp.data.model.Gear
import kotlinx.coroutines.flow.first
import java.io.IOException

/**
 * A betolteset vegzo osztaly.
 * @property repository a taroloja.
 */
class LoadOneGearUseCase(private val repository: GearRepository) {

    /**
     * betoltese az azonositoja alapjan.
     * @param id az azonositoja.
     * @return az elem.
     */
    suspend operator fun invoke(id: Int): Result<Gear> {
        return try {
            Result.success(repository.getById(id).first().asBaseModel())
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}