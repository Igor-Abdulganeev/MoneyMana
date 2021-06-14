package ru.gorinih.moneymana.presentation.ui.categorydetails.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.gorinih.moneymana.data.db.MDatabase
import ru.gorinih.moneymana.data.repository.CategoryRepositoryImpl
import ru.gorinih.moneymana.data.repository.CheckRepositoryImpl

@Suppress("UNCHECKED_CAST")
class CategoryDetailsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryDetailsViewModel::class.java)) {
            val db = MDatabase.getInstance(context)
            val repoCheck = CheckRepositoryImpl(db)
            val repoCategory = CategoryRepositoryImpl(db)
            return CategoryDetailsViewModel(repoCheck, repoCategory) as T
        } else throw IllegalArgumentException("Unfounded viewmodel $modelClass")
    }
}