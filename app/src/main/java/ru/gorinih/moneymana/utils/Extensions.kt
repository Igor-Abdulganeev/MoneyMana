package ru.gorinih.moneymana.utils

import android.view.View

fun View.calculateMaxSide(): Int =
    this.width.takeIf { it < this.height } ?: this.height

