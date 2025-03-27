package com.example.gearlistapp.domain.usecases.location

import com.example.gearlistapp.data.repository.LocationRepository

/**
 * A usecase-ket osszegujto osztaly.
 * @property repository a taroloja.
 */
class LocationUseCases(repository: LocationRepository) {
    val loads = LoadLocationUseCase(repository)
    val load = LoadOneLocationUseCase(repository)
    val save = SaveLocationUseCase(repository)
    val update = UpdateLocationUseCase(repository)
    val delete = DeleteLocationUseCase(repository)
}