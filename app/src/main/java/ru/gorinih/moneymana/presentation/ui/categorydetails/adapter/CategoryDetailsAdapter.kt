package ru.gorinih.moneymana.presentation.ui.categorydetails.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.gorinih.moneymana.presentation.model.CheckPresentation

class CategoryDetailsAdapter :
    ListAdapter<CheckPresentation, CategoryDetailsViewHolder>(CategoryDetailsDiffUtils()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryDetailsViewHolder {
        return CategoryDetailsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CategoryDetailsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}