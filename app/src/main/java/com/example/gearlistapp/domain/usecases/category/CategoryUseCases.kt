package com.example.gearlistapp.domain.usecases.category

import com.example.gearlistapp.data.repository.CategoryRepository

/**
 * A usecase-ket osszegujto osztaly.
 * @property repository a taroloja.
 */
class CategoryUseCases(repository: CategoryRepository) {
    val loads = LoadCategoryUseCase(repository)
    val load = LoadOneCategoryUseCase(repository)
    val save = SaveCategoryUseCase(repository)
    val update = UpdateCategoryUseCase(repository)
    val delete = DeleteCategoryUseCase(repository)
}