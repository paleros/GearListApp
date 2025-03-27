package com.example.gearlistapp.domain.usecases.location

import com.example.gearlistapp.data.model.Location
import com.example.gearlistapp.data.repository.LocationRepository
import kotlinx.coroutines.flow.first
import java.io.IOException

/**
 * A betolteset vegzo osztaly.
 * @property repository a taroloja.
 */
class LoadLocationUseCase(private val repository: LocationRepository) {

    /**
     * Az elem betoltese.
     * @return az elemek listaja.
     */
    suspend operator fun invoke(): Result<List<Location>> {
        return try {
            val entities = repository.getAll().first()
            Result.success(entities.map { it.asBaseModel() })
        } catch (e: IOException) {
            Result.failure(e)
        }
    }
}