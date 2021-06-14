package ru.gorinih.moneymana.domain

import kotlinx.coroutines.flow.Flow
import ru.gorinih.moneymana.data.model.CheckEntity

interface CheckRepository {
    fun testCheck(dateCheck: Long, sumCheck: Double): Boolean

    suspend fun insertCheck(check: CheckEntity): Boolean

    fun getChecksFromCategory(categoryId: Long): Flow<List<CheckEntity>>?
}