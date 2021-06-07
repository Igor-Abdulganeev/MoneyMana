package ru.gorinih.moneymana.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories"
)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null,
    @ColumnInfo(name = "image_category")
    val imageId: Int,
    @ColumnInfo(name = "title_category")
    val categoryName: String,
    @ColumnInfo(name = "active")
    val isActive: Boolean,
    @ColumnInfo(name = "sum_budget")
    val sumBudget: Int
)