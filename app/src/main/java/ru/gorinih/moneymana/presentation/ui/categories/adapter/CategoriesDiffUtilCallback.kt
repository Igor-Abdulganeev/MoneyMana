package ru.gorinih.moneymana.presentation.ui.categories.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.gorinih.moneymana.presentation.model.CategoryPresentation

class CategoriesDiffUtilCallback(
    private val oldList: List<CategoryPresentation>,
    private val newList: List<CategoryPresentation>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}