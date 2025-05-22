package com.example.gearlistapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gearlistapp.GearListApplication.Companion.templateRepository
import com.example.gearlistapp.data.model.Template
import com.example.gearlistapp.domain.usecases.template.TemplateUseCases
import com.example.gearlistapp.ui.model.TemplateUi
import com.example.gearlistapp.ui.model.asTemplate
import com.example.gearlistapp.ui.model.asTemplateUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class TemplateListState {
    object Loading : TemplateListState()
    data class Error(val error: Throwable) : TemplateListState()
    data class Result(val templateList: List<TemplateUi>) : TemplateListState()
}

/**
 * A sablon viewmodelje
 * @property templateOperations a sablon muveletek
 */
class TemplateViewModel(
    private val templateOperations: TemplateUseCases
) : ViewModel() {

    private val _state = MutableStateFlow<TemplateListState>(TemplateListState.Loading)
    val state = _state.asStateFlow()

    /**
     * A sablonok betoltese
     */
    fun loadTemplates() {
        viewModelScope.launch {
            try {
                val templates = templateOperations.loads().getOrThrow().map { it.asTemplateUi() }
                _state.value = TemplateListState.Result(
                    templateList = templates
                )
            } catch (e: Exception) {
                _state.value = TemplateListState.Error(e)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val templateOperations = TemplateUseCases(templateRepository)
                TemplateViewModel(
                    templateOperations
                )
            }
        }
    }

    /**
     * A sablon torlese
     * @param id a sablon azonositoja
     */
    fun delete(id: Int) {
        viewModelScope.launch {
            templateOperations.delete(id)
            loadTemplates()
        }
    }

    /**
     * A sablon elmentese
     */
    fun add(title: String, description: String, duration: Int, itemList: List<Int>, backgroundColor: Int,
            date: String = "", concrete: Boolean = false) {
        viewModelScope.launch {
            val newTemplate = TemplateUi(
                title = title,
                description = description,
                duration = duration,
                itemList = itemList,
                backgroundColor = backgroundColor,
                date = date,
                concrete = concrete
            )
            templateOperations.save(newTemplate.asTemplate())
            loadTemplates()
        }
    }

    /**
     * A sablon visszaadasa id alapjan
     * @param id a sablon id-je
     * @return a sablon
     */
    suspend fun getById(id: Int): TemplateUi? {
        return try {
            templateOperations.load(id).getOrNull()?.asTemplateUi()
        } catch (_: Exception) {
            null
        }
    }

    /**
     * A sablon frissitese
     * @param template a sablon
     */
    fun update(template: TemplateUi) {
        viewModelScope.launch {
            templateOperations.update(template.asTemplate())
            loadTemplates()
        }
    }

    /**
     * A sablon id alapjan
     * @param id a sablon id-je
     * @return a sablon
     */
    fun getById(id: Int, onResult: (Template?) -> Unit) {
        viewModelScope.launch {
            val template = try {
                templateOperations.load(id).getOrNull()
            } catch (_: Exception) {
                null
            }
            onResult(template)
        }
    }

}