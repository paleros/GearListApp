package com.example.gearlistapp.domain.usecases.template

import com.example.gearlistapp.data.model.Template
import com.example.gearlistapp.data.repository.TemplateRepository

/**
 * A frissiteset vegzo osztaly.
 * @property repository a taroloja.
 */
class UpdateTemplateUseCase(private val repository: TemplateRepository) {

    /**
     * frissitese.
     * @param category az elem.
     */
    suspend operator fun invoke(model: Template) {
        repository.update(model.asEntity())
    }

}