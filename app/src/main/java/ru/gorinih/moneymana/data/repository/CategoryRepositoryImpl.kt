package ru.gorinih.moneymana.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.gorinih.moneymana.data.db.ManaDatabase
import ru.gorinih.moneymana.data.model.CategoryEntity
import ru.gorinih.moneymana.domain.CategoriesRepository
import ru.gorinih.moneymana.presentation.model.CategoryPresentation

class CategoryRepositoryImpl(private val db: ManaDatabase) : CategoriesRepository {

    /*

    override fun getAllCategories(bool: Boolean): Flow<List<CategoryEntity>> =
        db.categoriesDao.getAllCategories(bool)

    override fun getActualCategories(startDay: Long, endDay: Long): Flow<List<CategoryPresentation>> {
        return db.categoriesDao.getActualCategories( true)
       // return db.categoriesDao.getActualCategories(startDay, endDay, true)
    }
*/

    override suspend fun insertCategoriesList(items: List<CategoryEntity>) {
        db.categoriesDao.insertCategoriesList(items)
    }

    override fun getAllCategoriesName(bool: Boolean): Flow<List<CategoryEntity>> =
        db.categoriesDao.getAllCategoriesName(bool)

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

}