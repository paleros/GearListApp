package com.example.gearlistapp.domain.usecases.category

import com.example.gearlistapp.data.model.Category
import com.example.gearlistapp.data.repository.CategoryRepository

/**
 * A frissiteset vegzo osztaly.
 * @property repository a taroloja.
 */
class UpdateCategoryUseCase(private val repository: CategoryRepository) {

    /**
     * frissitese.
     * @param category az elem.
     */
    suspend operator fun invoke(model: Category) {
        repository.update(model.asEntity())
    }

}