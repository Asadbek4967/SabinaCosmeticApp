package com.example.sabinacosmeticapplication.feature.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sabinacosmeticapplication.domain.usecase.category.GetCategoryTreeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryTreeUseCase: GetCategoryTreeUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState(isLoading = true))
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    fun onAction(action: CategoryUiAction) {
        when (action) {
            is CategoryUiAction.SelectRootCategory -> selectRootCategory(action.categoryId)
            is CategoryUiAction.ToggleExpandCategory -> toggleExpand(action.categoryId)
            is CategoryUiAction.OpenCategory -> Unit
            CategoryUiAction.RetryLoad -> loadCategories()
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                )
            }

            runCatching {
                getCategoryTreeUseCase()
            }.onSuccess { categories ->
                val selected = categories.firstOrNull()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        rootCategories = categories,
                        selectedRootCategoryId = selected?.id,
                        selectedRootCategory = selected,
                        expandedCategoryId = null,
                        errorMessage = null,
                    )
                }
            }.onFailure { throwable ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        rootCategories = emptyList(),
                        selectedRootCategoryId = null,
                        selectedRootCategory = null,
                        expandedCategoryId = null,
                        errorMessage = throwable.message ?: "Failed to load categories.",
                    )
                }
            }
        }
    }

    private fun selectRootCategory(categoryId: String) {
        val selected = _uiState.value.rootCategories.firstOrNull { it.id == categoryId } ?: return

        _uiState.update { currentState ->
            currentState.copy(
                selectedRootCategoryId = selected.id,
                selectedRootCategory = selected,
                expandedCategoryId = null,
            )
        }
    }

    private fun toggleExpand(categoryId: String) {
        _uiState.update { currentState ->
            currentState.copy(
                expandedCategoryId = if (currentState.expandedCategoryId == categoryId) {
                    null
                } else {
                    categoryId
                },
            )
        }
    }
}