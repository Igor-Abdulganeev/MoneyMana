package ru.gorinih.moneymana.presentation.ui.categorydetails.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.gorinih.moneymana.presentation.model.CheckPresentation

class CategoryDetailsDiffUtils : DiffUtil.ItemCallback<CheckPresentation>() {
    override fun areItemsTheSame(oldItem: CheckPresentation, newItem: CheckPresentation): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CheckPresentation,
        newItem: CheckPresentation
    ): Boolean {
        return oldItem == newItem
    }
}