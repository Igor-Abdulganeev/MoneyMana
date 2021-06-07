package ru.gorinih.moneymana.presentation.ui.categoriesmanager.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.gorinih.moneymana.presentation.model.CategoryPresentation

class CategoriesDiffUtil() : DiffUtil.ItemCallback<CategoryPresentation>() {
    override fun areItemsTheSame(
        oldItem: CategoryPresentation,
        newItem: CategoryPresentation
    ): Boolean = oldItem.id == newItem.id


    override fun areContentsTheSame(
        oldItem: CategoryPresentation,
        newItem: CategoryPresentation
    ): Boolean = oldItem == newItem
}