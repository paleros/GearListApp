package com.example.gearlistapp.domain.usecases.template

import com.example.gearlistapp.data.model.Template
import com.example.gearlistapp.data.repository.TemplateRepository

/**
 * A menteset vegzo osztaly.
 * @property repository a taroloja.
 */
class SaveTemplateUseCase(private val repository: TemplateRepository) {

    /**
     * mentese.
     * @param model a model.
     */
    suspend operator fun invoke(model: Template) {
        repository.insert(model.asEntity())
    }

}