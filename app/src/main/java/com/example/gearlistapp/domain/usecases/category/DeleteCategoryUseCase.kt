package com.example.gearlistapp.domain.usecases.category

import com.example.gearlistapp.data.repository.CategoryRepository

/**
 * A torleset vegzo osztaly.
 * @property repository a taroloja.
 */
class DeleteCategoryUseCase(private val repository: CategoryRepository) {

    /**
     * Elem torlese az azonositoja alapjan.
     * @param id az elem azonositoja.
     */
    suspend operator fun invoke(id: Int) {
        repository.deleteById(id)
    }

}