package ru.gorinih.moneymana.presentation.ui.categoriesmanager.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.gorinih.moneymana.data.model.CategoryEntity
import ru.gorinih.moneymana.domain.CategoriesRepository
import ru.gorinih.moneymana.presentation.model.CategoryPresentation

class CategoriesManagerViewModel(private val repoCategories: CategoriesRepository) : ViewModel() {

    private var _listCategories = MutableLiveData<List<CategoryPresentation>>()
    val listCategories: LiveData<List<CategoryPresentation>> get() = _listCategories

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            repoCategories.getAllCategories()
                .map {
                    mapperCategoriesEntityToPresentation(it)
                }
                .collect {
                    _listCategories.value = it
                }
        }
    }

    suspend fun updateCategory(item: CategoryPresentation) {
        viewModelScope.launch {
            repoCategories.updateCategory(mapperCategoryPresentationToEntity(item))
        }
    }

    private fun mapperCategoriesEntityToPresentation(item: List<CategoryEntity>): List<CategoryPresentation> {
        return item.map {
            CategoryPresentation(
                it.id!!,
                it.imageId,
                it.categoryName,
                if (it.isActive) 1 else 0,
                it.sumBudget
            )
        }
    }

    private fun mapperCategoryPresentationToEntity(item: CategoryPresentation): CategoryEntity {
        return CategoryEntity(
            item.id,
            item.image_category,
            item.title_category,
            item.sum_check == 1L,
            item.sum_budget
        )
    }
}