package com.example.gearlistapp.domain.usecases.gear

import com.example.gearlistapp.data.model.Gear
import com.example.gearlistapp.data.repository.GearRepository

/**
 * A frissiteset vegzo osztaly.
 * @property repository a taroloja.
 */
class UpdateGearUseCase(private val repository: GearRepository) {

    /**
     * frissitese.
     * @param category az elem.
     */
    suspend operator fun invoke(model: Gear) {
        repository.update(model.asEntity())
    }

}