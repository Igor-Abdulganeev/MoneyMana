package ru.gorinih.moneymana.presentation.ui.categoriesmanager.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.gorinih.moneymana.presentation.model.CategoryPresentation

class CategoriesManagerAdapter(private val listener: (CategoryPresentation) -> Unit) :
    ListAdapter<CategoryPresentation, CategoriesManagerViewHolder>(CategoriesDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesManagerViewHolder {
        return CategoriesManagerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CategoriesManagerViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }
}