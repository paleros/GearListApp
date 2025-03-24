package com.example.gearlistapp.domain.usecases.all

import com.example.gearlistapp.data.entities.BaseEntity
import com.example.gearlistapp.data.entities.CategoryEntity
import com.example.gearlistapp.data.repository.Repository

/**
 * A torleset vegzo osztaly.
 * @property repository a taroloja.
 */
class DeleteUseCase(private val repository: Repository<BaseEntity>) {

    /**
     * Elem torlese az azonositoja alapjan.
     * @param id az elem azonositoja.
     */
    suspend operator fun invoke(id: Int) {
        repository.deleteById(id)
    }

}