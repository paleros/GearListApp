package com.example.gearlistapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gearlistapp.R
import com.example.gearlistapp.data.dao.CategoryDao
import com.example.gearlistapp.data.dao.GearDao
import com.example.gearlistapp.data.dao.LocationDao
import com.example.gearlistapp.data.entities.CategoryEntity
import com.example.gearlistapp.data.entities.GearEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A felszereles viewmodelje
 * @param gearDao a felszereles dao-ja
 * @param locationDao a helyszin dao-ja
 * @param categoryDao a kategoria dao-ja
 */
@HiltViewModel
open class GearViewModel @Inject constructor(
    private val gearDao: GearDao,
    private val locationDao: LocationDao,
    private val categoryDao: CategoryDao
) : ViewModel() {

    private val _gearList = MutableStateFlow<List<GearEntity>>(emptyList())
    open val gearList = _gearList.asStateFlow()

    init {
        loadGears()
    }

    /**
     * A felszerelesek betoltese
     */
    private fun loadGears() {
        viewModelScope.launch {
            _gearList.value = gearDao.getAllGears().firstOrNull() ?: emptyList()
        }
    }

    /**
     * Egy felszereles entitas lekerese az azonositoja alapjan
     * @param id a felszereles azonositoja
     * @return a felszereles entitas
     */
    open fun getCategoryById(id: Int): Flow<CategoryEntity> {
        return categoryDao.getCategoryById(id)
    }

    /**
     * Egy helyszin nevenek lekerese az azonositoja alapjan
     * @param id a helyszin azonositoja
     * @return a helyszin neve
     */
    suspend fun getLocationNameById(id: Int): String {
        return locationDao.getLocationById(id).firstOrNull()?.name ?: R.string.n_a.toString()
    }

    /**
     * Egy felszereles entitas lekerese az azonositoja alapjan
     * @param id a felszereles azonositoja
     * @return a felszereles entitas
     */
    fun insertGear(gear: GearEntity) {
        viewModelScope.launch {
            gearDao.insertGear(gear)
            loadGears()
        }
    }

    /**
     * Egy felszereles entitas modositasa
     * @param gear a felszereles entitas
     */
    fun updateGear(gear: GearEntity) {
        viewModelScope.launch {
            gearDao.updateGear(gear)
            loadGears()
        }
    }

    /**
     * Egy felszereles entitas torlese
     * @param gear a felszereles entitas
     */
    fun deleteGear(gear: GearEntity) {
        viewModelScope.launch {
            gearDao.deleteGear(gear)
            loadGears()
        }
    }
}
