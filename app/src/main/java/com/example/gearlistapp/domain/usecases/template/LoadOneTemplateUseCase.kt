package com.example.gearlistapp.domain.usecases.template

import com.example.gearlistapp.data.model.Template
import com.example.gearlistapp.data.repository.TemplateRepository
import kotlinx.coroutines.flow.first
import java.io.IOException

/**
 * A betolteset vegzo osztaly.
 * @property repository a taroloja.
 */
class LoadOneTemplateUseCase(private val repository: TemplateRepository) {

    /**
     * betoltese az azonositoja alapjan.
     * @param id az azonositoja.
     * @return az elem.
     */
    suspend operator fun invoke(id: Int): Result<Template> {
        return try {
            Result.success(repository.getById(id).first().asBaseModel())
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}