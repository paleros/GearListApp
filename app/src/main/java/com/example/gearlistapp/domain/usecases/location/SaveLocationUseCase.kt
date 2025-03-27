package com.example.gearlistapp.domain.usecases.location

import com.example.gearlistapp.data.model.Location
import com.example.gearlistapp.data.repository.LocationRepository

/**
 * A menteset vegzo osztaly.
 * @property repository a taroloja.
 */
class SaveLocationUseCase(private val repository: LocationRepository) {

    /**
     * mentese.
     * @param model a model.
     */
    suspend operator fun invoke(model: Location) {
        repository.insert(model.asEntity())
    }

}