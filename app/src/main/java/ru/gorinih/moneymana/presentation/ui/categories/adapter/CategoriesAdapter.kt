package ru.gorinih.moneymana.presentation.ui.categories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.gorinih.moneymana.databinding.ItemManaBinding
import ru.gorinih.moneymana.presentation.model.CategoryPresentation

class CategoriesAdapter(private val listener: (Long) -> Unit) :
    RecyclerView.Adapter<CategoriesViewHolder>() {

    private var items = listOf<CategoryPresentation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            ItemManaBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun getItemCount(): Int = items.size

    fun bindItems(newItems: List<CategoryPresentation>) {
        val diffUtilCallback = CategoriesDiffUtilCallback(items, newItems)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffUtilCallback)
        diffResult.dispatchUpdatesTo(this)
        items = newItems
    }
}
