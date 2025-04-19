package com.example.gearlistapp.domain.usecases.template

import com.example.gearlistapp.data.repository.TemplateRepository

/**
 * A usecase-ket osszegujto osztaly.
 * @property repository a taroloja.
 */
class TemplateUseCases(repository: TemplateRepository) {
    val loads = LoadTemplateUseCase(repository)
    val load = LoadOneTemplateUseCase(repository)
    val save = SaveTemplateUseCase(repository)
    val update = UpdateTemplateUseCase(repository)
    val delete = DeleteTemplateUseCase(repository)
}