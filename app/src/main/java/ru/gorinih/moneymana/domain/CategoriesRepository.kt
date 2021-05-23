package ru.gorinih.moneymana.domain

import kotlinx.coroutines.flow.Flow
import ru.gorinih.moneymana.data.model.CategoryEntity
import ru.gorinih.moneymana.presentation.model.CategoryData

interface CategoriesRepository {
    suspend fun insertCategoriesList(items: List<CategoryEntity>)

    fun getAllCategories(bool: Boolean): Flow<List<CategoryEntity>>

    fun getActualCategories(startDay: Long, endDay: Long): Flow<List<CategoryData>>

}