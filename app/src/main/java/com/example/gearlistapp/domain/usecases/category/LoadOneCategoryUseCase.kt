package com.example.gearlistapp.domain.usecases.category

import com.example.gearlistapp.data.model.Category
import com.example.gearlistapp.data.repository.CategoryRepository
import kotlinx.coroutines.flow.first
import java.io.IOException

/**
 * A betolteset vegzo osztaly.
 * @property repository a taroloja.
 */
class LoadOneCategoryUseCase(private val repository: CategoryRepository) {

    /**
     * betoltese az azonositoja alapjan.
     * @param id az azonositoja.
     * @return az elem.
     */
    suspend operator fun invoke(id: Int): Result<Category> {
        return try {
            Result.success(repository.getById(id).first().asBaseModel())
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}