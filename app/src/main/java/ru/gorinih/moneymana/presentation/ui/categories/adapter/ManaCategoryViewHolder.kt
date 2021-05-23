package ru.gorinih.moneymana.presentation.ui.categories.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.gorinih.moneymana.databinding.ItemManaBinding
import ru.gorinih.moneymana.presentation.model.CategoryData

class ManaCategoryViewHolder(
    private val binding: ItemManaBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(manaCategory: CategoryData) {
        with(binding) {
            itemManaNameTextView.text = manaCategory.title_category
            itemManaImageView.setImageResource(manaCategory.image_category)
            itemManaCustomProgressBar.progress = 50 // manaCategory.percentRemained
            itemManaSumLimitTextView.text = manaCategory.sum_check?.toString() ?: "0"
/*
            if (manaCategory.sumRemained >= 0) {
                itemManaSumRemainedTextView.text =
                    itemView.context.getString(R.string.sum_remained, manaCategory.sumRemained)
            } else {
                itemManaSumRemainedTextView.text =
                    itemView.context.getString(R.string.overrun_sum, manaCategory.sumRemained)
            }
*/
//            itemManaSumRemainedTextView.setTextColor(
//                ContextCompat.getColor(itemView.context, manaCategory.status)
//            )

//            manaConstraintLayout.setOnClickListener {
//                Log.d("CameraFragment", "Вызов фрагмента с id-$manaCategory")
//                listener.invoke(manaCategory)
//            }
        }
    }
}
