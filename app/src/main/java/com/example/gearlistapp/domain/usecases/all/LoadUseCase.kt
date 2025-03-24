package com.example.gearlistapp.domain.usecases.all

import com.example.gearlistapp.data.entities.BaseEntity
import com.example.gearlistapp.data.entities.CategoryEntity
import com.example.gearlistapp.data.model.BaseModel
import com.example.gearlistapp.data.model.Category
import com.example.gearlistapp.data.model.Gear
import com.example.gearlistapp.data.model.Location
import com.example.gearlistapp.data.repository.Repository
import kotlinx.coroutines.flow.first
import java.io.IOException

/**
 * A betolteset vegzo osztaly.
 * @property repository a taroloja.
 */
class LoadUseCase(private val repository: Repository<BaseEntity>) {

    /**
     * Az elem betoltese.
     * @return az elem listaja.
     */
    suspend operator fun invoke(): Result<List<BaseModel>> {
        return try {
            val entities = repository.getAll().first()
            Result.success(entities.map { it.asBaseModel() })
        } catch (e: IOException) {
            Result.failure(e)
        }
    }
}