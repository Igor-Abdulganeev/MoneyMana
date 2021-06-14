package ru.gorinih.moneymana.presentation.ui.categorydetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.gorinih.moneymana.R
import ru.gorinih.moneymana.databinding.ItemCheckBinding
import ru.gorinih.moneymana.presentation.model.CheckPresentation
import ru.gorinih.moneymana.utils.DateTimeConverter

class CategoryDetailsViewHolder(private val binding: ItemCheckBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: CheckPresentation
    ) {
        with(binding) {
            checkDatetimeTextview.text =
                DateTimeConverter().setDateTimeLongToString(item.date_check)
            checkSumTextview.text = item.sum_check.toString()
        }
    }

    companion object {
        fun from(parent: ViewGroup): CategoryDetailsViewHolder {
            return CategoryDetailsViewHolder(
                ItemCheckBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}