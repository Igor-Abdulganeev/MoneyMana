package ru.gorinih.moneymana.presentation.ui.categories.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.gorinih.moneymana.data.model.BudgetEntity
import ru.gorinih.moneymana.domain.BudgetRepository
import ru.gorinih.moneymana.domain.CategoriesRepository
import ru.gorinih.moneymana.presentation.model.CategoryData
import ru.gorinih.moneymana.utils.DateTimeConverter

class ManaCategoriesViewModel(
    private val repoCategory: CategoriesRepository
//    ,private val repoBudget: BudgetRepository
) : ViewModel() {
    private val _manaCategories = MutableLiveData<List<CategoryData>>()
    val manaCategories: LiveData<List<CategoryData>> get() = _manaCategories

    private val _budget = MutableLiveData<BudgetEntity>()
    val budget: LiveData<BudgetEntity> get() = _budget

    private val dateTime = DateTimeConverter()

    @InternalCoroutinesApi
    fun getUserManaState() {
        viewModelScope.launch {
            repoCategory.getActualCategories(
                dateTime.getFirstDay(),
                dateTime.getLastDay()
            )
                .filterNotNull()
                .collect {
                    _manaCategories.value = it
                }
/*
            val period = DateTimeConverter().getPeriod(0)
            settingsRepository.getBudget(period.month, period.year)?.filterNotNull()
                // manaRepository.getPrimaryBudgetSettings()
                ?.collect {
                    Log.d("CameraFragment", "settingsRepository.getBudget $it")
                    _primarySettings.value = it
                }
*/
        }
    }

/*
    fun getBudget() {
        viewModelScope.launch {
            repoBudget.getActualBudget(dateTime.getFirstDay(), dateTime.getLastDay())
                .map {
                    if (it == null) {
                        repoBudget.insertBudget(
                            BudgetEntity(
                                null,
                                dateTime.getFirstDay(),
                                dateTime.getLastDay(),
                                0
                            )
                        )
                        BudgetEntity(0,0,0,0)
                    } else it
                }
                .collect {
                    _budget.value = it }
        }
    }
*/

}