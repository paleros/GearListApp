package com.example.gearlistapp.domain.usecases.category

import com.example.gearlistapp.data.model.Category
import com.example.gearlistapp.data.repository.CategoryRepository
import kotlinx.coroutines.flow.first
import java.io.IOException

/**
 * A betolteset vegzo osztaly.
 * @property repository a taroloja.
 */
class LoadCategoryUseCase(private val repository: CategoryRepository) {

    /**
     * Az elem betoltese.
     * @return az elemek listaja.
     */
    suspend operator fun invoke(): Result<List<Category>> {
        return try {
            val entities = repository.getAll().first()
            Result.success(entities.map { it.asBaseModel() })
        } catch (e: IOException) {
            Result.failure(e)
        }
    }
}