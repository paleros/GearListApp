package com.example.gearlistapp.domain.usecases.gear

import com.example.gearlistapp.data.entities.GearEntity
import com.example.gearlistapp.data.entities.asEntity
import com.example.gearlistapp.data.model.Gear
import com.example.gearlistapp.data.repository.Repository

/**
 * A felszereles menteset vegzo osztaly.
 * @property repository a felszerelesek taroloja.
 */
class SaveGearUseCase(private val repository: Repository<GearEntity>) {

    /**
     * Felszereles mentese.
     * @param gear a felszereles.
     */
    suspend operator fun invoke(gear: Gear) {
        repository.insert(gear.asEntity())
    }

}