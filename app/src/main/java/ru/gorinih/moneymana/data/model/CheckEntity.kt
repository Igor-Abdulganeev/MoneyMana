package ru.gorinih.moneymana.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "checks"
)
data class CheckEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null,
    @ColumnInfo(name = "id_category")
    val idCategory: Long,
    @ColumnInfo(name = "day_check")
    val dayCheck: Long,
    @ColumnInfo(name = "date_check")
    val dateCheck: Long,
    @ColumnInfo(name = "sum_check")
    val sumCheck: Double,
    @ColumnInfo(name = "fn_check")
    val fnCheck: Long,
    @ColumnInfo(name = "i_check")
    val iCheck: Long,
    @ColumnInfo(name = "fp_check")
    val fpCheck: Long
)