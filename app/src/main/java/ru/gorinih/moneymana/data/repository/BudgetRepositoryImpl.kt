package ru.gorinih.moneymana.data.repository

/*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.gorinih.moneymana.data.db.ManaDatabase
import ru.gorinih.moneymana.data.model.BudgetEntity
import ru.gorinih.moneymana.domain.BudgetRepository
import ru.gorinih.moneymana.presentation.model.BudgetPresentation

class BudgetRepositoryImpl(private val db: ManaDatabase): BudgetRepository {
    override fun getActualBudget(startDay: Long, endDay: Long): Flow<BudgetPresentation?> =
        db.budgetDAO.getCurrentBudget(startDay, endDay)

    override suspend fun insertBudget(item: BudgetEntity) = withContext(Dispatchers.IO) {
        db.budgetDAO.insertBudget(item)
    }

}*/
