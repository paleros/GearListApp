package com.example.gearlistapp.domain.usecases.gear

import com.example.gearlistapp.data.repository.GearRepository

/**
 * A usecase-ket osszegujto osztaly.
 * @property repository a taroloja.
 */
class GearUseCases(repository: GearRepository) {
    val loads = LoadGearUseCase(repository)
    val load = LoadOneGearUseCase(repository)
    val save = SaveGearUseCase(repository)
    val update = UpdateGearUseCase(repository)
    val delete = DeleteGearUseCase(repository)
}