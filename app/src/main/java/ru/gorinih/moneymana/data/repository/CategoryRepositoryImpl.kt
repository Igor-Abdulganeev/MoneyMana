package ru.gorinih.moneymana.data.repository

import kotlinx.coroutines.flow.Flow
import ru.gorinih.moneymana.data.db.ManaDatabase
import ru.gorinih.moneymana.data.model.CategoryEntity
import ru.gorinih.moneymana.domain.CategoriesRepository
import ru.gorinih.moneymana.presentation.model.CategoryData

class CategoryRepositoryImpl(private val db: ManaDatabase) : CategoriesRepository {

    override suspend fun insertCategoriesList(items: List<CategoryEntity>) {
        db.categoriesDao.insertCategoriesList(items)
    }

    override fun getAllCategories(bool: Boolean): Flow<List<CategoryEntity>> =
        db.categoriesDao.getAllCategories(bool)

    override fun getActualCategories(startDay: Long, endDay: Long): Flow<List<CategoryData>> {
        return db.categoriesDao.getActualCategories( true)
       // return db.categoriesDao.getActualCategories(startDay, endDay, true)
    }
}