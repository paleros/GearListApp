package com.example.gearlistapp.domain.usecases.gear

import com.example.gearlistapp.data.entities.GearEntity
import com.example.gearlistapp.data.entities.asEntity
import com.example.gearlistapp.data.model.Gear
import com.example.gearlistapp.data.repository.Repository

/**
 * A felszereles frissiteset vegzo osztaly.
 * @property repository a felszerelesek taroloja.
 */
class UpdateGearUseCase(private val repository: Repository<GearEntity>) {

    /**
     * Felszereles frissitese.
     * @param gear a felszereles.
     */
    suspend operator fun invoke(gear: Gear) {
        repository.update(gear.asEntity())
    }

}

//TODO usecase-ek a többi entitásra is
//TODO createviewmodel és a többi folytatni az AUT leírás alapján
//TODO cél megjeleníteni az adatbázis elemeit az alkalmazásban
