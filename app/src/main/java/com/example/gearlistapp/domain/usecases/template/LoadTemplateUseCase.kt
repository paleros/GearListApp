package com.example.gearlistapp.domain.usecases.template

import com.example.gearlistapp.data.model.Template
import com.example.gearlistapp.data.repository.TemplateRepository
import kotlinx.coroutines.flow.first
import java.io.IOException

/**
 * A betolteset vegzo osztaly.
 * @property repository a taroloja.
 */
class LoadTemplateUseCase(private val repository: TemplateRepository) {

    /**
     * Az elem betoltese.
     * @return az elemek listaja.
     */
    suspend operator fun invoke(): Result<List<Template>> {
        return try {
            val entities = repository.getAll().first()
            Result.success(entities.map { it.asBaseModel() })
        } catch (e: IOException) {
            Result.failure(e)
        }
    }
}