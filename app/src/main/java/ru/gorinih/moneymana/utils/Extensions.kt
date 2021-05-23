package ru.gorinih.moneymana.utils

import android.view.View

fun View.calculateMinSide(): Int =
    this.width.takeIf { it < this.height } ?: this.height

