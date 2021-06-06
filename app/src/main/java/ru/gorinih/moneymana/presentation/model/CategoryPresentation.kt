package ru.gorinih.moneymana.presentation.model

data class CategoryPresentation(
    val id: Long,
    val image_category: Int,
    val title_category: String,
    val sum_check: Long?,
    val sum_budget: Int
)
