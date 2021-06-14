package ru.gorinih.moneymana.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.gorinih.moneymana.data.db.MDatabase
import ru.gorinih.moneymana.data.model.CheckEntity
import ru.gorinih.moneymana.domain.CheckRepository

class CheckRepositoryImpl(private val db: MDatabase) : CheckRepository {

    override fun testCheck(dateCheck: Long, sumCheck: Double): Boolean {
        var bool = false
        val list = db.checksDao.testCheck(dateCheck, sumCheck)
        list?.let {
            bool = true
        }
        return bool
    }

    override suspend fun insertCheck(check: CheckEntity): Boolean = withContext(Dispatchers.IO) {
        db.checksDao.testCheck(check.dateCheck, check.sumCheck)?.let {
            return@withContext false
        } ?: run {
            db.checksDao.insert(check)
            return@withContext true
        }
    }

    override fun getChecksFromCategory(categoryId: Long): Flow<List<CheckEntity>>? =
        db.checksDao.getChecksFromCategory(categoryId = categoryId)

}
