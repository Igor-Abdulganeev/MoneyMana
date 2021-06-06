package ru.gorinih.moneymana.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.gorinih.moneymana.data.model.BudgetEntity
import ru.gorinih.moneymana.presentation.model.BudgetPresentation

@Dao
interface BudgetDAO {

    @Query("SELECT A.start_time, A.end_time, A.sum_budget, SUM(B.sum_check) AS sum_remaining FROM budget A LEFT JOIN checks B ON B.date_check>=:startDay AND date_check<=:endDay WHERE start_time=:startDay AND end_time=:endDay GROUP BY A.start_time")
    fun getCurrentBudget(startDay: Long, endDay: Long): Flow<BudgetPresentation?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(item: BudgetEntity)
}