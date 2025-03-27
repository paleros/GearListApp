package com.example.gearlistapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gearlistapp.GearApplication
import com.example.gearlistapp.data.entities.CategoryEntity
import com.example.gearlistapp.data.entities.GearEntity
import com.example.gearlistapp.data.entities.LocationEntity
import com.example.gearlistapp.domain.usecases.gear.GearUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class GearListState {
    object Loading : GearListState()
    data class Error(val error: Throwable) : GearListState()
    data class Result(val gearList: List<GearEntity>) : GearListState()
}

/**
 * A felszereles viewmodelje
 * @property gearOperations a felszereles muveletek
 */
@HiltViewModel
open class GearViewModel @Inject constructor(
    private val gearOperations : GearUseCases
) : ViewModel() {

    private val _gearList = MutableStateFlow<GearListState>(GearListState.Loading)
    open val gearList = _gearList.asStateFlow()

    init {
        loadGears()
    }

    /**
     * A felszerelesek betoltese
     */
    fun loadGears() {
        viewModelScope.launch {
            try {
                _gearList.value = GearListState.Loading
                val gears = gearOperations.loads().getOrThrow().map { it.asEntity() }
                _gearList.value = GearListState.Result(
                    gearList = gears
                )
            } catch (e: Exception) {
                _gearList.value = GearListState.Error(e)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val gearOperations = GearUseCases(GearApplication.gearRepository)
                GearViewModel(
                    gearOperations
                )
            }
        }
    }

    /**
     * A felszereles kategoriajanak lekerese
     * @param id a felszereles azonositoja
     * @return a kategoria
     */
    suspend fun getGearCategoryById(id: Int) : CategoryEntity? {
        val gearCategoryId = GearApplication.gearRepository.getById(id).firstOrNull()?.categoryId
        return GearApplication.categoryRepository.getById(gearCategoryId).firstOrNull()

    }

    /**
     * A felszereles helyszinenek lekerese
     * @param id a felszereles azonositoja
     * @return a helyszin
     */
    suspend fun getGearLocationById(id : Int) : LocationEntity? {
        val gearLocationId = GearApplication.gearRepository.getById(id).firstOrNull()?.locationId
        return GearApplication.locationRepository.getById(gearLocationId).firstOrNull()
    }
}