package com.example.gearlistapp.domain.usecases.location

import com.example.gearlistapp.data.model.Location
import com.example.gearlistapp.data.repository.LocationRepository

/**
 * A frissiteset vegzo osztaly.
 * @property repository a taroloja.
 */
class UpdateLocationUseCase(private val repository: LocationRepository) {

    /**
     * frissitese.
     * @param category az elem.
     */
    suspend operator fun invoke(model: Location) {
        repository.update(model.asEntity())
    }

}