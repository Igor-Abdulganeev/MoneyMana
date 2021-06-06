package ru.gorinih.moneymana.presentation.model

data class BudgetPresentation(
    val start_time: Long,
    val end_time: Long,
    val sum_budget: Int,
    val sum_remaining: Int
)