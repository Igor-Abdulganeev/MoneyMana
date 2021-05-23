package ru.gorinih.moneymana.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.gorinih.moneymana.data.model.BudgetEntity

@Dao
interface BudgetDAO {

    @Query("SELECT * FROM budget WHERE start_time=:startDay AND end_time=:endDay")
    fun getCurrentBudget(startDay: Long, endDay: Long): Flow<BudgetEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(item: BudgetEntity)
}