package com.example.gearlistapp.presentation.viewmodel

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gearlistapp.GearApplication.Companion.categoryRepository
import com.example.gearlistapp.domain.usecases.category.CategoryUseCases
import com.example.gearlistapp.ui.model.CategoryUi
import com.example.gearlistapp.ui.model.asCategory
import com.example.gearlistapp.ui.model.asCategoryUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class CategoryListState {
    object Loading : CategoryListState()
    data class Error(val error: Throwable) : CategoryListState()
    data class Result(val categoryList: List<CategoryUi>) : CategoryListState()
}

/**
 * Kategoria viewmodel
 * @param categoryOperations a kategoria muveletek
 */
class CategoryViewModel(
    private val categoryOperations: CategoryUseCases
) : ViewModel() {

    private val _state = MutableStateFlow<CategoryListState>(CategoryListState.Loading)
    val state = _state.asStateFlow()

    fun loadCategories() {
        viewModelScope.launch {
            try {
                val categories = categoryOperations.loads().getOrThrow().map { it.asCategoryUi() }
                _state.value = CategoryListState.Result(
                    categoryList = categories
                )
            } catch (e: Exception) {
                _state.value = CategoryListState.Error(e)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val categoryOperations = CategoryUseCases(categoryRepository)
                CategoryViewModel(
                    categoryOperations
                )
            }
        }
    }

    /**
     * Kategoria torlese
     * @param id a kategoria id-je
     */
    fun delete(id: Int) {
        viewModelScope.launch {
            try {
                categoryOperations.delete(id)
            } catch (e: Exception) {
                _state.value = CategoryListState.Error(e)
            }
        }
        loadCategories()
    }

    /**
     * Kategoria elmentese
     * @param name a kategoria neve
     * @param color a kategoria szine
     * @param iconRes a kategoria ikonja
     */
    fun add(name: String, color: Int, iconRes: ImageVector) {
        val iconName = "Icons.Default." + iconRes.name.replaceFirst("Filled.".toRegex(), "")
        viewModelScope.launch {
            try {
                val category = CategoryUi(
                    name = name,
                    color = color,
                    iconName = iconName
                )
                categoryOperations.save(category.asCategory())
            } catch (e: Exception) {
                _state.value = CategoryListState.Error(e)
            }
        }
        loadCategories()
    }

    /**
     * A kategoria visszaadasa id alapjan
     * @param id a kategoria id-je
     * @return a kategoria
     */
    suspend fun getById(id: Int): CategoryUi? {
        return try {
            categoryOperations.load(id).getOrNull()?.asCategoryUi()
        } catch (e: Exception) {
            null
        }
    }
}