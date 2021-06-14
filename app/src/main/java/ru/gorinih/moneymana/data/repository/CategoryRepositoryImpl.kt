package ru.gorinih.moneymana.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.gorinih.moneymana.data.db.MDatabase
import ru.gorinih.moneymana.data.model.CategoryEntity
import ru.gorinih.moneymana.domain.CategoriesRepository
import ru.gorinih.moneymana.presentation.model.BudgetPresentation
import ru.gorinih.moneymana.presentation.model.CategoryPresentation

class CategoryRepositoryImpl(private val db: MDatabase) : CategoriesRepository {

    override suspend fun insertCategoriesList(items: List<CategoryEntity>) {
        db.categoriesDao.insertCategoriesList(items)
    }

    override suspend fun updateCategory(item: CategoryEntity) {
        withContext(Dispatchers.IO) {
            db.categoriesDao.updateCategory(item)
        }
    }

    override suspend fun getActualBudget(startDay: Long, endDay: Long): Flow<BudgetPresentation> =
        withContext(Dispatchers.IO) {
            db.categoriesDao.getActualBudget(startDay, endDay)
        }

    override suspend fun getAllCategories(): Flow<List<CategoryEntity>> =
        db.categoriesDao.getAllCategories()

    override fun getAllCategoriesIsActive(bool: Boolean): Flow<List<CategoryEntity>> =
        db.categoriesDao.getAllCategoriesIsActive(bool)

    override suspend fun getCategoriesWithSum(
        firstDay: Long,
        lastDay: Long
    ): Flow<List<CategoryPresentation>> =
        withContext(Dispatchers.IO) {
            db.categoriesDao.getCategoriesWithSum(
                true,
                firstDay,
                lastDay
            )
        }

    override suspend fun getCategoryWithSum(
        categoryId: Long
    ): Flow<CategoryPresentation> =
        withContext(Dispatchers.IO) {
            db.categoriesDao.getCategoryWithSum(
                categoryId
            )
        }
}