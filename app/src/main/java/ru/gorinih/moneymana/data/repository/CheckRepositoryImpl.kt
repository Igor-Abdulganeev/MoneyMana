package ru.gorinih.moneymana.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gorinih.moneymana.data.db.ManaDatabase
import ru.gorinih.moneymana.data.model.CheckEntity
import ru.gorinih.moneymana.domain.CheckRepository

class CheckRepositoryImpl(private val db: ManaDatabase) : CheckRepository {

    override fun testCheck(dateCheck: Long, sumCheck: Double): Boolean {
        return if (db.checksDao.testCheck(dateCheck, sumCheck) == null) return false else true
    }

    override suspend fun insertCheck(check: CheckEntity) = withContext(Dispatchers.IO) {
        db.checksDao.insert(check)
    }
}