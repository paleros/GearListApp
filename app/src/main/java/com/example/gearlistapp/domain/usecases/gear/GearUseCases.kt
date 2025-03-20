package com.example.gearlistapp.domain.usecases.gear

import com.example.gearlistapp.data.entities.GearEntity
import com.example.gearlistapp.data.repository.Repository

/**
 * A felszereles usecase-ket osszegujto osztaly.
 * @property repository a felszereles taroloja.
 */
class GearUseCases(repository: Repository<GearEntity>) {
    val loadTodos = LoadGearsUseCase(repository)
    val loadTodo = LoadGearUseCase(repository)
    val saveTodo = SaveGearUseCase(repository)
    val updateTodo = UpdateGearUseCase(repository)
    val deleteTodo = DeleteGearUseCase(repository)
}