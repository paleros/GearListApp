package com.example.gearlistapp.domain.usecases.gear

import com.example.gearlistapp.data.entities.GearEntity
import com.example.gearlistapp.data.repository.Repository

/**
 * A felszereles torleset vegzo osztaly.
 * @property repository a felszereles taroloja.
 */
class DeleteGearUseCase(private val repository: Repository<GearEntity>) {

    /**
     * Felszereles torlese az azonositoja alapjan.
     * @param id a felszereles azonositoja.
     */
    suspend operator fun invoke(id: Int) {
        repository.deleteById(id)
    }

}