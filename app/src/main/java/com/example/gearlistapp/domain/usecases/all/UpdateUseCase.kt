package com.example.gearlistapp.domain.usecases.all

import com.example.gearlistapp.data.entities.BaseEntity
import com.example.gearlistapp.data.model.BaseModel
import com.example.gearlistapp.data.repository.Repository

/**
 * A frissiteset vegzo osztaly.
 * @property repository a taroloja.
 */
class UpdateUseCase(private val repository: Repository<BaseEntity>) {

    /**
     * frissitese.
     * @param category az elem.
     */
    suspend operator fun invoke(model: BaseModel) {
        repository.update(model.asEntity())
    }

}

//TODO createviewmodel és a többi folytatni az AUT leírás alapján
//TODO cél megjeleníteni az adatbázis elemeit az alkalmazásban
