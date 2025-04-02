package com.example.gearlistapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gearlistapp.GearApplication.Companion.locationRepository
import com.example.gearlistapp.domain.usecases.location.LocationUseCases
import com.example.gearlistapp.ui.model.LocationUi
import com.example.gearlistapp.ui.model.asLocation
import com.example.gearlistapp.ui.model.asLocationUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class LocationListState {
    object Loading : LocationListState()
    data class Error(val error: Throwable) : LocationListState()
    data class Result(val locationList: List<LocationUi>) : LocationListState()
}

/**
 * Helyszin viewmodel
 * @param locationOperations a helyszin muveletek
 */
class LocationViewModel(
    private val locationOperations: LocationUseCases
) : ViewModel() {

    private val _state = MutableStateFlow<LocationListState>(LocationListState.Loading)
    val state = _state.asStateFlow()

    /**
     * Helyszin lista betoltese
     */
    fun loadLocations() {
        viewModelScope.launch {
            try {
                val locations = locationOperations.loads().getOrThrow().map { it.asLocationUi() }
                _state.value = LocationListState.Result(
                    locationList = locations
                )
            } catch (e: Exception) {
                _state.value = LocationListState.Error(e)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val locationOperations = LocationUseCases(locationRepository)
                LocationViewModel(
                    locationOperations
                )
            }
        }
    }

    /**
     * Helyszin torlese
     * @param id a torlendo helyszin id-je
     */
    fun delete(id: Int) {
        viewModelScope.launch {
            try {
                locationOperations.delete(id)
            } catch (e: Exception) {
                _state.value = LocationListState.Error(e)
            }
        }
        loadLocations()
    }

    /**
     * Helyszin elmentese
     * @param name a helyszin neve
     */
    fun add(name: String) {
        viewModelScope.launch {
            try {
                val location = LocationUi(
                    name = name,
                )
                locationOperations.save(location.asLocation())
            } catch (e: Exception) {
                _state.value = LocationListState.Error(e)
            }
        }
        loadLocations()
    }
}