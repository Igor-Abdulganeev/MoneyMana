package ru.gorinih.moneymana.presentation.ui.categories.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.gorinih.moneymana.presentation.model.CategoryData

class ManaCategoriesDiffUtilCallback(
    private val oldList: List<CategoryData>,
    private val newList: List<CategoryData>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}