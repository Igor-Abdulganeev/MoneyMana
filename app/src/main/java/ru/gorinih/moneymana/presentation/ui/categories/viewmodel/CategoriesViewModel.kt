package ru.gorinih.moneymana.presentation.ui.categories.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import ru.gorinih.moneymana.data.model.BudgetEntity
import ru.gorinih.moneymana.domain.BudgetRepository
import ru.gorinih.moneymana.domain.CategoriesRepository
import ru.gorinih.moneymana.presentation.model.BudgetPresentation
import ru.gorinih.moneymana.presentation.model.CategoryPresentation
import ru.gorinih.moneymana.utils.DateTimeConverter

class CategoriesViewModel(
    private val repoCategory: CategoriesRepository, private val repoBudget: BudgetRepository
) : ViewModel() {
    private val _manaCategories = MutableLiveData<List<CategoryPresentation>>()
    val manaCategories: LiveData<List<CategoryPresentation>> get() = _manaCategories

    private val _budget = MutableLiveData<BudgetPresentation>()
    val budget: LiveData<BudgetPresentation> get() = _budget

    private val dateTime = DateTimeConverter()

    @InternalCoroutinesApi
    fun getUserManaState() {
        viewModelScope.launch {
            repoCategory.getCategoriesWithSum(
                dateTime.getFirstDay(),
                dateTime.getLastDay()
            )
                .filterNotNull()
                .collect {
                    _manaCategories.value = it
                }
        }
    }

    private suspend fun insertNowBudget() =
        repoBudget.insertBudget(
            BudgetEntity(
                null,
                dateTime.getFirstDay(),
                dateTime.getLastDay(),
                0
            )
        )

    fun getBudget() {
        viewModelScope.launch {
            repoBudget.getActualBudget(dateTime.getFirstDay(), dateTime.getLastDay())
                .collect {
                    it ?: run {
                        insertNowBudget()
                    }
                    _budget.value = it ?: BudgetPresentation(0, 0, 0, 0)
                }
        }
    }
}