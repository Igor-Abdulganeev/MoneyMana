package ru.gorinih.moneymana.presentation.ui.categories.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.gorinih.moneymana.R
import ru.gorinih.moneymana.databinding.ItemManaBinding
import ru.gorinih.moneymana.presentation.model.CategoryPresentation
import ru.gorinih.moneymana.utils.getPercentage

class CategoriesViewHolder(
    private val binding: ItemManaBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(manaCategory: CategoryPresentation) {
        with(binding) {
            itemManaNameTextView.text = manaCategory.title_category
            itemManaImageView.setImageResource(manaCategory.image_category)
            itemManaSumLimitTextView.text = manaCategory.sum_check?.toString() ?: "0"
            val sumRemained: Int = (manaCategory.sum_budget - (manaCategory.sum_check ?: 0)).toInt()

            val textRemained =
                if (sumRemained >= 0) itemView.context.getString(R.string.text_remained)
                else itemView.context.getString(R.string.text_overrun)
                //            itemManaSumRemainedTextView.setTextColor(
//                ContextCompat.getColor(itemView.context, manaCategory.status)
//            )
            itemManaSumRemainedTextView.text =
                itemView.context.getString(R.string.sum_remained, textRemained, sumRemained)


            itemManaCustomProgressBar.progress = sumRemained.getPercentage(manaCategory.sum_budget)

//            manaConstraintLayout.setOnClickListener {
//                Log.d("CameraFragment", "Вызов фрагмента с id-$manaCategory")
//                listener.invoke(manaCategory)
//            }
/*
            {
                val percentRemained: Int = sumRemained.getPercentage()
                val status: Int = when (percentRemained) {
                    in 20..50 -> R.color.shiny_yellow_green
                    in 51..100 -> R.color.indian_green
                    else -> R.color.pale_red
                }

                private fun Int.getPercentage() = times(100).div(if (sum_budget != 0) sum_budget else 1)
            }
*/


        }
    }


}
