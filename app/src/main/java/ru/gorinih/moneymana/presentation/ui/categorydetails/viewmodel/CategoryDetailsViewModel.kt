package ru.gorinih.moneymana.presentation.ui.categorydetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.gorinih.moneymana.data.model.CheckEntity
import ru.gorinih.moneymana.domain.CategoriesRepository
import ru.gorinih.moneymana.domain.CheckRepository
import ru.gorinih.moneymana.presentation.model.CategoryPresentation
import ru.gorinih.moneymana.presentation.model.CheckPresentation
import ru.gorinih.moneymana.utils.DateTimeConverter

class CategoryDetailsViewModel(
    private val repoCheck: CheckRepository,
    private val repoCategory: CategoriesRepository
) : ViewModel() {

    private var _listChecks = MutableSharedFlow<List<CheckPresentation>>()
    val listChecks: SharedFlow<List<CheckPresentation>> get() = _listChecks

    private val date = DateTimeConverter()

    fun loadChecks(categoryId: Long) {
        viewModelScope.launch {
            repoCheck.getChecksFromCategory(categoryId)?.map {
                mapperCheckEntityToPresentation(it)
            }
                ?.collect {
                    _listChecks.emit(it)
                }
        }
    }

    suspend fun getCategoryProperty(categoryId: Long): Flow<CategoryPresentation> {
        val repoJob = viewModelScope.async {
            repoCategory.getCategoryWithSum(categoryId)
        }
        return repoJob.await()
    }

    private fun mapperCheckEntityToPresentation(list: List<CheckEntity>): List<CheckPresentation> {
        return list.map {
            CheckPresentation(
                id = it.id ?: 0,
                date_check = it.dateCheck,
                sum_check = it.sumCheck
            )
        }
    }
}