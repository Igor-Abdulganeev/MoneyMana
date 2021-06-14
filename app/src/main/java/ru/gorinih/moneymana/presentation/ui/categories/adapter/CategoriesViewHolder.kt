package ru.gorinih.moneymana.presentation.ui.categories.adapter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.gorinih.moneymana.R
import ru.gorinih.moneymana.databinding.ItemManaBinding
import ru.gorinih.moneymana.presentation.model.CategoryPresentation
import ru.gorinih.moneymana.utils.getPercentage

class CategoriesViewHolder(
    private val binding: ItemManaBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        manaCategory: CategoryPresentation,
        listener: (Long) -> Unit
    ) {
        with(binding) {
            manaConstraintLayout.setOnClickListener {
                listener.invoke(manaCategory.id)
            }
            itemManaNameTextView.text = manaCategory.title_category
            itemManaImageView.setImageResource(manaCategory.image_category)
            itemManaSumLimitTextView.text = manaCategory.sum_check?.toString() ?: "0"
            val sumRemained: Int = (manaCategory.sum_budget - (manaCategory.sum_check ?: 0)).toInt()

            itemSumRemainedTextView.text =
                itemView.context.getString(R.string.sum_remained, sumRemained)
            if (sumRemained < 0) {
                itemRemainedTextView.text = itemView.context.getString(R.string.text_overrun)
                itemSumRemainedTextView.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.pale_red
                    )
                )
            } else {
                itemRemainedTextView.text = itemView.context.getString(R.string.text_remained)
                itemSumRemainedTextView.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white_light
                    )
                )
            }
            itemManaCustomProgressBar.progress = sumRemained.getPercentage(manaCategory.sum_budget)
        }
    }


}
