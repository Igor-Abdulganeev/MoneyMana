package ru.gorinih.moneymana.presentation.ui.categories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.gorinih.moneymana.databinding.ItemManaBinding
import ru.gorinih.moneymana.presentation.model.CategoryData

class ManaCategoriesAdapter() : //(private val listener: (ManaCategory) -> Unit) :
    RecyclerView.Adapter<ManaCategoryViewHolder>() {

    private var items = listOf<CategoryData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManaCategoryViewHolder {
        return ManaCategoryViewHolder(
            ItemManaBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            //   ,listener
        )
    }

    override fun onBindViewHolder(holder: ManaCategoryViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun bindItems(newItems: List<CategoryData>) {
        val diffUtilCallback = ManaCategoriesDiffUtilCallback(items, newItems)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffUtilCallback)
        diffResult.dispatchUpdatesTo(this)
        items = newItems
    }
}
