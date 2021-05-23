package ru.gorinih.moneymana.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.gorinih.moneymana.data.model.CategoryEntity
import ru.gorinih.moneymana.presentation.model.CategoryData

@Dao
interface CategoriesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoriesList(categoryEntityList: List<CategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM categories WHERE active = :bool ORDER BY id ASC")
    fun getAllCategories(bool: Boolean): Flow<List<CategoryEntity>>

    //@Query("SELECT A.id, A.image_category, A.title_category, B.sum_check FROM categories AS A INNER JOIN checks as B ON B.id=A.id AND B.date_check>=:startDay AND B.date_check<=:endDay WHERE active = :bool ORDER BY A.id ASC")
//    fun getActualCategories(startDay: Long, endDay: Long, bool: Boolean): Flow<List<CategoryData>>
//cool    @Query("SELECT A.id, A.image_category, A.title_category, 0 as sum_check FROM categories AS A WHERE active = :bool ORDER BY A.id ASC")
    @Query("SELECT A.id, A.image_category, A.title_category, SUM(B.sum_check) as sum_check FROM categories AS A LEFT JOIN checks as B ON B.id_category=A.id WHERE active = :bool GROUP BY A.ID ORDER BY A.id ASC")
    fun getActualCategories( bool: Boolean): Flow<List<CategoryData>>

}