package com.example.gearlistapp.domain.usecases.location

import com.example.gearlistapp.data.model.Location
import com.example.gearlistapp.data.repository.LocationRepository
import kotlinx.coroutines.flow.first
import java.io.IOException

/**
 * A betolteset vegzo osztaly.
 * @property repository a taroloja.
 */
class LoadOneLocationUseCase(private val repository: LocationRepository) {

    /**
     * betoltese az azonositoja alapjan.
     * @param id az azonositoja.
     * @return az elem.
     */
    suspend operator fun invoke(id: Int): Result<Location> {
        return try {
            Result.success(repository.getById(id).first().asBaseModel())
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}