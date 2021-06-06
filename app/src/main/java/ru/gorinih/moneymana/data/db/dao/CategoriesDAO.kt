package ru.gorinih.moneymana.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.gorinih.moneymana.data.model.CategoryEntity
import ru.gorinih.moneymana.presentation.model.CategoryPresentation

@Dao
interface CategoriesDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategoriesList(categoryEntityList: List<CategoryEntity>)

/*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryEntity: CategoryEntity)
*/

    @Query("SELECT * FROM categories WHERE active = :bool ORDER BY id ASC")
    fun getAllCategoriesName(bool: Boolean): Flow<List<CategoryEntity>>

    //@Query("SELECT A.id, A.image_category, A.title_category, B.sum_check FROM categories AS A INNER JOIN checks as B ON B.id=A.id AND B.date_check>=:startDay AND B.date_check<=:endDay WHERE active = :bool ORDER BY A.id ASC")
//    fun getActualCategories(startDay: Long, endDay: Long, bool: Boolean): Flow<List<CategoryData>>
//cool    @Query("SELECT A.id, A.image_category, A.title_category, 0 as sum_check FROM categories AS A WHERE active = :bool ORDER BY A.id ASC")
//    @Query("SELECT A.id, A.image_category, A.title_category, SUM(B.sum_check) as sum_check FROM categories AS A LEFT JOIN checks as B ON B.id_category=A.id WHERE active = :bool GROUP BY A.ID ORDER BY A.id ASC")
//    fun getActualCategories( bool: Boolean): Flow<List<CategoryPresentation>>

    //    @Query("SELECT A.id, A.image_category, A.title_category, SUM(B.sum_check) AS sum_check, 0 AS sum_budget FROM categories AS A LEFT JOIN checks as B ON B.id_category=A.id WHERE A.active = :bool GROUP BY A.id ORDER BY A.id ASC")
    @Query("SELECT A.id, A.image_category, A.title_category, SUM(B.sum_check) AS sum_check, C.sum_budget FROM categories AS A LEFT JOIN checks as B ON B.id_category=A.id AND B.day_check BETWEEN :firstDay AND :lastDay INNER JOIN budget AS C ON C.start_time>=:firstDay AND C.end_time<=:lastDay WHERE A.active = :bool GROUP BY A.id ORDER BY A.id ASC")
    fun getCategoriesWithSum(
        bool: Boolean,
        firstDay: Long,
        lastDay: Long
    ): Flow<List<CategoryPresentation>>

}