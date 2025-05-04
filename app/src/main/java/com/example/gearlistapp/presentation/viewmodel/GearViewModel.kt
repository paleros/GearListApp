package com.example.gearlistapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gearlistapp.GearApplication.Companion.gearRepository
import com.example.gearlistapp.data.model.Gear
import com.example.gearlistapp.ui.model.GearUi
import com.example.gearlistapp.domain.usecases.gear.GearUseCases
import com.example.gearlistapp.ui.model.asGear
import com.example.gearlistapp.ui.model.asGearUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class GearListState {
    object Loading : GearListState()
    data class Error(val error: Throwable) : GearListState()
    data class Result(val gearList: List<GearUi>) : GearListState()
}

/**
 * A felszereles viewmodelje
 * @property gearOperations a felszereles muveletek
 */
class GearViewModel(
    private val gearOperations: GearUseCases
) : ViewModel() {

    private val _state = MutableStateFlow<GearListState>(GearListState.Loading)
    val state = _state.asStateFlow()

    /**
     * A felszerelesek betoltese
     */
    fun loadGears() {
        viewModelScope.launch {
            try {
                val gears = gearOperations.loads().getOrThrow().map { it.asGearUi() }
                _state.value = GearListState.Result(
                    gearList = gears
                )
            } catch (e: Exception) {
                _state.value = GearListState.Error(e)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val gearOperations = GearUseCases(gearRepository)
                GearViewModel(
                    gearOperations
                )
            }
        }
    }

    /**
     * A felszereles torlese
     * @param id a felszereles azonositoja
     */
    fun delete(id: Int) {
        viewModelScope.launch {
            gearOperations.delete(id)
            loadGears()
        }
    }

    /**
     * A felszereles elmentese
     * @param name a felszereles neve
     * @param description a felszereles leirasa
     * @param categoryId a felszereles kategoria azonositoja
     * @param locationId a felszereles helyszin azonositoja
     * @param inPackage bepakoltuk-e mar a csomagba, csak akkor lenyeges, ha konkret
     * @param pieces hany darab kell belole, csak akkor lenyeges, ha konkret
     * @param parent konkret-e a felszereles
     * @param onResult a callback, amely visszaadja a felszereles id-jat
     * @return a felszereles id-je
     */
    fun add(name: String, description: String, categoryId: Int, locationId: Int,
            inPackage: Boolean = false, pieces: Int = 1, parent: Int = -1,
            onResult: (Int) -> Unit) {
        viewModelScope.launch {
            val newGear = GearUi(
                name = name,
                description = description,
                categoryId = categoryId,
                locationId = locationId,
                inPackage = inPackage,
                pieces = pieces,
                parent = parent
            )
            val id = gearOperations.save(newGear.asGear())
            loadGears()
            onResult(id)
        }
    }

    /**
     * A felszereles visszaadasa id alapjan
     * @param id a felszereles id-je
     * @return a felszereles
     */
    suspend fun getById(id: Int): GearUi? {
        return try {
            gearOperations.load(id).getOrNull()?.asGearUi()
        } catch (_: Exception) {
            null
        }
    }

    /**
     * A felszereles frissitese
     * @param gear a felszereles
     */
    fun update(gear: GearUi) {
        viewModelScope.launch {
            gearOperations.update(gear.asGear())
            loadGears()
        }
    }

    /**
     * A felszereles id alapjan
     * @param id a felszereles id-je
     * @return a felszereles
     */
    fun getById(id: Int, onResult: (Gear?) -> Unit) {
        viewModelScope.launch {
            val gear = try {
                gearOperations.load(id).getOrNull()
            } catch (_: Exception) {
                null
            }
            onResult(gear)
        }
    }

}