package com.example.gearlistapp.domain.usecases.template

import com.example.gearlistapp.data.repository.TemplateRepository

/**
 * A torleset vegzo osztaly.
 * @property repository a taroloja.
 */
class DeleteTemplateUseCase(private val repository: TemplateRepository) {

    /**
     * Elem torlese az azonositoja alapjan.
     * @param id az elem azonositoja.
     */
    suspend operator fun invoke(id: Int) {
        repository.deleteById(id)
    }

}