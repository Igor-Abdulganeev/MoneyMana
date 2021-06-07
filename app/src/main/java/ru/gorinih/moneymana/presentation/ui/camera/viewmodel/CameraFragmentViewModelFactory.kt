package ru.gorinih.moneymana.presentation.ui.camera.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.gorinih.moneymana.data.db.MDatabase
import ru.gorinih.moneymana.data.repository.CategoryRepositoryImpl
import ru.gorinih.moneymana.data.repository.CheckRepositoryImpl


@Suppress("UNCHECKED_CAST")
class CameraFragmentViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CameraFragmentViewModel::class.java)) {
            val db = MDatabase.getInstance(context = context)
            val repoCategory = CategoryRepositoryImpl(db = db)
            val repoCheck = CheckRepositoryImpl(db = db)
            return CameraFragmentViewModel(
                repoCategory = repoCategory,
                repoCheck = repoCheck
            ) as T
        } else throw IllegalArgumentException("$modelClass is not registered ViewModel")
    }
}