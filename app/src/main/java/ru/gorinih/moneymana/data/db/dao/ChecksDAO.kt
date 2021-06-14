package ru.gorinih.moneymana.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.gorinih.moneymana.data.model.CategoryEntity
import ru.gorinih.moneymana.data.model.CheckEntity

@Dao
interface ChecksDAO {
    //ManaRepositoryImpl
/*
    @Query("SELECT * FROM checks ORDER BY id ASC")
    fun getAllChecks(): Flow<List<CheckEntity>>
*/

    //ManaFragmentCategoryRepository
    @Query("SELECT * FROM checks WHERE id_category == :categoryId ORDER BY date_check DESC")
    fun getChecksFromCategory(categoryId: Long): Flow<List<CheckEntity>>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(checkEntity: CheckEntity)

/*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChecksList(checkEntityList: List<CheckEntity>)
*/

    @Query("SELECT * FROM checks WHERE date_check=:dateCheck AND sum_check=:sumCheck LIMIT 1")
    fun testCheck(dateCheck: Long, sumCheck: Double): CheckEntity?
}
