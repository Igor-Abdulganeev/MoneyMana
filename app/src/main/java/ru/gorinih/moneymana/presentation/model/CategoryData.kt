package ru.gorinih.moneymana.presentation.model

data class CategoryData (
        val id: Long,
        val image_category: Int,
        val title_category: String,
        val sum_check: Long?
        )