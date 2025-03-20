package com.example.gearlistapp.domain.usecases.gear

import com.example.gearlistapp.data.entities.GearEntity
import com.example.gearlistapp.data.entities.asGear
import com.example.gearlistapp.data.model.Gear
import com.example.gearlistapp.data.repository.Repository
import kotlinx.coroutines.flow.first
import java.io.IOException

/**
 * A felszereles betolteset vegzo osztaly.
 * @property repository a felszereles taroloja.
 */
class LoadGearUseCase(private val repository: Repository<GearEntity>) {

    /**
     * Felszereles betoltese az azonositoja alapjan.
     * @param id a felszereles azonositoja.
     * @return a felszereles.
     */
    suspend operator fun invoke(id: Int): Result<Gear> {
        return try {
            Result.success(repository.getById(id).first().asGear())
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}