package com.example.gearlistapp.domain.usecases.location

import com.example.gearlistapp.data.repository.LocationRepository

/**
 * A torleset vegzo osztaly.
 * @property repository a taroloja.
 */
class DeleteLocationUseCase(private val repository: LocationRepository) {

    /**
     * Elem torlese az azonositoja alapjan.
     * @param id az elem azonositoja.
     */
    suspend operator fun invoke(id: Int) {
        repository.deleteById(id)
    }

}