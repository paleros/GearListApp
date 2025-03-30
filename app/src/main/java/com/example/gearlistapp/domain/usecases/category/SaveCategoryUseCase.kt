package com.example.gearlistapp.domain.usecases.category

import com.example.gearlistapp.data.model.Category
import com.example.gearlistapp.data.model.Gear
import com.example.gearlistapp.data.repository.CategoryRepository

/**
 * A menteset vegzo osztaly.
 * @property repository a taroloja.
 */
class SaveCategoryUseCase(private val repository: CategoryRepository) {

    /**
     * mentese.
     * @param model a model.
     */
    suspend operator fun invoke(model: Category) {
        repository.insert(model.asEntity())
    }

}