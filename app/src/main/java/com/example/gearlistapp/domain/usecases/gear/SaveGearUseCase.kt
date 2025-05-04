package com.example.gearlistapp.domain.usecases.gear

import com.example.gearlistapp.data.model.Gear
import com.example.gearlistapp.data.repository.GearRepository

/**
 * A menteset vegzo osztaly.
 * @property repository a taroloja.
 */
class SaveGearUseCase(private val repository: GearRepository) {

    /**
     * mentese.
     * @param model a model.
     * @return a mentett elem azonositoja.
     */
    suspend operator fun invoke(model: Gear) :Int {
        var id = repository.insert(model.asEntity())
        return id
    }

}