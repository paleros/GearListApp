package com.example.gearlistapp.domain.usecases.all

import com.example.gearlistapp.data.entities.BaseEntity
import com.example.gearlistapp.data.repository.Repository

/**
 * A usecase-ket osszegujto osztaly.
 * @property repository a taroloja.
 */
class UseCases(repository: Repository<BaseEntity>) {
    val loads = LoadUseCase(repository)
    val load = LoadOneUseCase(repository)
    val save = SaveUseCase(repository)
    val update = UpdateUseCase(repository)
    val delete = DeleteUseCase(repository)
}