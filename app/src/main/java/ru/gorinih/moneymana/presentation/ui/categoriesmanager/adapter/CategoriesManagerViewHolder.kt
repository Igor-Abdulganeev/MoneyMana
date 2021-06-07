package ru.gorinih.moneymana.presentation.ui.categoriesmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.gorinih.moneymana.databinding.ItemCategoryManagerBinding
import ru.gorinih.moneymana.presentation.model.CategoryPresentation

class CategoriesManagerViewHolder(private val binding: ItemCategoryManagerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: CategoryPresentation
        //       listener: (Int)->Unit
    ) {
        with(binding) {
            categoryNameEdittext.setText(item.title_category)
            budgetEdittext.setText(item.sum_budget.toString())
            categoryImageview.setImageResource(item.image_category)
        }
    }

    companion object {
        fun from(parent: ViewGroup): CategoriesManagerViewHolder {
            return CategoriesManagerViewHolder(
                ItemCategoryManagerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}