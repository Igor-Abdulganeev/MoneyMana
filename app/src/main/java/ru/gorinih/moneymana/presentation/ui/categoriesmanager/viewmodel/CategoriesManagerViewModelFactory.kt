package ru.gorinih.moneymana.presentation.ui.categoriesmanager.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.gorinih.moneymana.data.db.MDatabase
import ru.gorinih.moneymana.data.repository.CategoryRepositoryImpl

@Suppress("UNCHECKED_CAST")
class CategoriesManagerViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoriesManagerViewModel::class.java)) {
            val database = MDatabase.getInstance(context = context)
            val repoCategories = CategoryRepositoryImpl(database)
            return CategoriesManagerViewModel(repoCategories = repoCategories) as T
        } else {
            throw IllegalArgumentException("Unfounded viewmodel $modelClass")
        }
    }
}