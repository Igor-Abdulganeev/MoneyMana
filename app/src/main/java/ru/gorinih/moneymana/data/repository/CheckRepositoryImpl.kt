package ru.gorinih.moneymana.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gorinih.moneymana.data.db.MDatabase
import ru.gorinih.moneymana.data.model.CheckEntity
import ru.gorinih.moneymana.domain.CheckRepository

class CheckRepositoryImpl(private val db: MDatabase) : CheckRepository {

    override fun testCheck(dateCheck: Long, sumCheck: Double): Boolean {
        var bool = false
        val list = db.checksDao.testCheck(dateCheck, sumCheck)
        Log.d("QWERTY", "$list = ")
        list?.let {
            bool = true
            Log.d("QWERTY", "$list == $bool ")

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
}
