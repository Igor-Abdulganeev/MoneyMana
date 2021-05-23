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
    @Query("SELECT * FROM checks ORDER BY id ASC")
    fun getAllChecks(): Flow<List<CheckEntity>>

    //ManaFragmentCategoryRepository
    @Query("SELECT * FROM checks WHERE id_category == :idCategory ORDER BY id ASC")
    fun getAllChecksFromCategory(idCategory: Long): Flow<List<CheckEntity>>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(checkEntity: CheckEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChecksList(checkEntityList: List<CheckEntity>)

}