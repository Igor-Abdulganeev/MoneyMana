package ru.gorinih.moneymana.presentation.ui.camera.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.gorinih.moneymana.data.db.ManaDatabase
import ru.gorinih.moneymana.data.repository.CategoryRepositoryImpl


@Suppress("UNCHECKED_CAST")
class CameraFragmentViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CameraFragmentViewModel::class.java)) {
            val db = ManaDatabase.getInstance(context = context)
            val repo = CategoryRepositoryImpl(db = db)
            return CameraFragmentViewModel(repo = repo) as T
        } else throw IllegalArgumentException("$modelClass is not registered ViewModel")
    }
}