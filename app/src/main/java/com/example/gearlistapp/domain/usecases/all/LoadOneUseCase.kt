package com.example.gearlistapp.domain.usecases.all

import com.example.gearlistapp.data.entities.BaseEntity
import com.example.gearlistapp.data.model.BaseModel
import com.example.gearlistapp.data.repository.Repository
import kotlinx.coroutines.flow.first
import java.io.IOException

/**
 * A betolteset vegzo osztaly.
 * @property repository a taroloja.
 */
class LoadOneUseCase(private val repository: Repository<BaseEntity>) {

    /**
     * betoltese az azonositoja alapjan.
     * @param id az azonositoja.
     * @return az elem.
     */
    suspend operator fun invoke(id: Int): Result<BaseModel> {
        return try {
            Result.success(repository.getById(id).first().asBaseModel())
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}