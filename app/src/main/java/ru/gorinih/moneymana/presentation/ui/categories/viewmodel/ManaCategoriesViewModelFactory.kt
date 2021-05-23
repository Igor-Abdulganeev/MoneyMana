package ru.gorinih.moneymana.presentation.ui.categories.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.gorinih.moneymana.data.db.ManaDatabase
//import ru.gorinih.moneymana.data.repository.BudgetRepositoryImpl
import ru.gorinih.moneymana.data.repository.CategoryRepositoryImpl

@Suppress("UNCHECKED_CAST")
class ManaCategoriesViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ManaCategoriesViewModel::class.java)) {
            val db = ManaDatabase.getInstance(context = context)
            val repoCategory = CategoryRepositoryImpl(db = db)
 //           val repoBudget = BudgetRepositoryImpl(db = db)
            return ManaCategoriesViewModel(repoCategory = repoCategory) as T
//            ,repoBudget = repoBudget) as T
        } else throw IllegalArgumentException("$modelClass is not registered ViewModel")

    }
}