package ru.gorinih.moneymana.domain

import kotlinx.coroutines.flow.Flow
import ru.gorinih.moneymana.data.model.CategoryEntity
import ru.gorinih.moneymana.presentation.model.BudgetPresentation
import ru.gorinih.moneymana.presentation.model.CategoryPresentation

interface CategoriesRepository {

    fun getAllCategoriesName(bool: Boolean): Flow<List<CategoryEntity>>

    suspend fun insertCategoriesList(items: List<CategoryEntity>)

    suspend fun getCategoriesWithSum(
        firstDay: Long,
        lastDay: Long
    ): Flow<List<CategoryPresentation>>
//suspend fun getCategoriesWithSum(): Flow<List<CategoryPresentation>>

    suspend fun getActualBudget(startDay: Long, endDay: Long): Flow<BudgetPresentation>

}