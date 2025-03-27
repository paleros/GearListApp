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
     */
    suspend operator fun invoke(model: Gear) {
        repository.insert(model.asEntity())
    }

}