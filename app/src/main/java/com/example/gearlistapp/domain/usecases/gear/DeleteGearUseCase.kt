package com.example.gearlistapp.domain.usecases.gear

import com.example.gearlistapp.data.repository.GearRepository

/**
 * A torleset vegzo osztaly.
 * @property repository a taroloja.
 */
class DeleteGearUseCase(private val repository: GearRepository) {

    /**
     * Elem torlese az azonositoja alapjan.
     * @param id az elem azonositoja.
     */
    suspend operator fun invoke(id: Int) {
        repository.deleteById(id)
    }

}