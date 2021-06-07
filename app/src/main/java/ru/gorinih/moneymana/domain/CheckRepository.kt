package ru.gorinih.moneymana.domain

import ru.gorinih.moneymana.data.model.CheckEntity

interface CheckRepository {
    fun testCheck(dateCheck: Long, sumCheck: Double): Boolean

    suspend fun insertCheck(check: CheckEntity): Boolean
}