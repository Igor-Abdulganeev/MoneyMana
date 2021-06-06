package ru.gorinih.moneymana.utils

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat

fun View.calculateMinSide(): Int =
    this.width.takeIf { it < this.height } ?: this.height

fun View.changeColor(context: Context, color: Int) {
    setBackgroundColor(
        ContextCompat.getColor(
            context,
            color
        )
    )
}

fun Int.getPercentage(sum: Int) = times(100).div(if (sum != 0) sum else 1)

