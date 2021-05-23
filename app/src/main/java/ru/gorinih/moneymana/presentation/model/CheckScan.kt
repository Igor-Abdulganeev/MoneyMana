package ru.gorinih.moneymana.presentation.model

data class CheckScan(
    val dateCheck: String,
    val category: CategoryScan,
    val sumCheck: Double,
    val fnCheck: Long,
    val iCheck: Long,
    val fpCheck: Long
)

