package com.example.gearlistapp.domain.usecases.all

import com.example.gearlistapp.data.entities.BaseEntity
import com.example.gearlistapp.data.model.BaseModel
import com.example.gearlistapp.data.repository.Repository

/**
 * A menteset vegzo osztaly.
 * @property repository a taroloja.
 */
class SaveUseCase(private val repository: Repository<BaseEntity>) {

    /**
     * mentese.
     * @param model a model.
     */
    suspend operator fun invoke(model: BaseModel) {
        repository.insert(model.asEntity())
    }

}