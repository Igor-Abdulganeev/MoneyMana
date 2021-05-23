package ru.gorinih.moneymana.domain

import kotlinx.coroutines.flow.Flow
import ru.gorinih.moneymana.data.model.BudgetEntity

interface BudgetRepository {
    fun getActualBudget(startDay: Long, endDay: Long): Flow<BudgetEntity?>

    suspend fun insertBudget(item: BudgetEntity)
}