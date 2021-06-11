package ru.gorinih.moneymana.presentation.ui.categoriesmanager.adapter

import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.gorinih.moneymana.R
import ru.gorinih.moneymana.databinding.ItemCategoryManagerBinding
import ru.gorinih.moneymana.presentation.model.CategoryPresentation
import ru.gorinih.moneymana.utils.changeColor
import ru.gorinih.moneymana.utils.toBool

class CategoriesManagerViewHolder(private val binding: ItemCategoryManagerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: CategoryPresentation,
        listener: (CategoryPresentation) -> Unit
    ) {
        with(binding) {
            categoryNameEdittext.setText(item.title_category)
            budgetEdittext.setText(item.sum_budget.toString())
            categoryImageview.setImageResource(item.image_category)
            categoryImageview.tag = item.image_category
            categoryImageview.setOnClickListener {
                val resourceImage = changeImage(categoryImageview.tag as Int)
                categoryImageview.setImageResource(resourceImage)
                categoryImageview.tag = resourceImage
                checkStatus(item, binding)
            }

            when (item.sum_check) {
                1L -> activeCheckedTextView.isChecked = true
                0L -> activeCheckedTextView.isChecked = false
            }

            if (item.id == 1L) {
                activeCheckedTextView.isEnabled = false
                categoryNameEdittext.isEnabled = false
                categoryImageview.setOnClickListener(null)
            } else {
                activeCheckedTextView.isEnabled = true
                categoryNameEdittext.isEnabled = true
            }
            budgetEdittext.setOnKeyListener { _, _, event ->
                if (budgetEdittext.length() == 0) budgetEdittext.setText("0")
                when (event.action) {
                    KeyEvent.ACTION_UP -> {
                        checkStatus(item, binding)
                    }
                    else -> false
                }
            }
            categoryNameEdittext.setOnKeyListener { _, _, event ->
                when (event.action) {
                    KeyEvent.ACTION_UP -> {
                        checkStatus(item, binding)
                    }
                    else -> false
                }
            }
            applyButton.setOnClickListener {
                listener.invoke(
                    CategoryPresentation(
                        item.id,
                        categoryImageview.tag as Int,
                        categoryNameEdittext.text.toString(),
                        if (activeCheckedTextView.isChecked) 1 else 0,
                        budgetEdittext.text.toString().toInt()
                    )
                )
                checkStatus(item, binding)
            }
            activeCheckedTextView.setOnClickListener {
                activeCheckedTextView.isChecked = !activeCheckedTextView.isChecked
                checkStatus(item, binding)
            }
            checkStatus(item, binding)
        }
    }

    private fun changeImage(current: Int): Int =
        symbolImage.getOrElse(symbolImage.indexOf(current) + 1) { symbolImage.first() }

    private fun checkStatus(
        item: CategoryPresentation,
        itemBinding: ItemCategoryManagerBinding
    ): Boolean {
        with(itemBinding) {
            if (item.sum_budget != budgetEdittext.text.toString().toInt()
                || item.title_category != categoryNameEdittext.text.toString()
                || item.image_category != categoryImageview.tag
                || item.sum_check?.toBool() != activeCheckedTextView.isChecked
            ) {
                applyButton.changeColor(itemView.context, R.color.primaryColor)
                applyButton.isEnabled = true
            } else {
                applyButton.changeColor(itemView.context, R.color.light_grey)
                applyButton.isEnabled = false
            }
        }
        return itemBinding.applyButton.isEnabled
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

        private val symbolImage = listOf<Int>(
            R.drawable.ambulance,
            R.drawable.beaker_check,
            R.drawable.drama_masks,
            R.drawable.food,
            R.drawable.gas_station,
            R.drawable.hiking,
            R.drawable.tshirt_crew_outline,
            R.drawable.weight_lifter
        )
    }
}
