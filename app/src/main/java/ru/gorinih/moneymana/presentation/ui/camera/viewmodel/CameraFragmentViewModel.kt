package ru.gorinih.moneymana.presentation.ui.camera.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.gorinih.moneymana.data.model.CategoryEntity
import ru.gorinih.moneymana.data.repository.CategoryRepositoryImpl
import ru.gorinih.moneymana.presentation.model.CategoryScan
import ru.gorinih.moneymana.presentation.model.CheckScan
import ru.gorinih.moneymana.presentation.model.ResultScan

class CameraFragmentViewModel(private val repo: CategoryRepositoryImpl) : ViewModel() {

    private var _listSpinner = MutableLiveData<List<CategoryScan>>()
    val listSpinner: LiveData<List<CategoryScan>>
        get() = _listSpinner

    fun checkScan(scan: String): ResultScan {
        return try {
            val result = convertToCheckScan(scan)
            ResultScan(result.dateCheck, result.sumCheck.toString())
        } catch (ex: Throwable) {
            ResultScan("", "")
        }
    }

    @InternalCoroutinesApi
    fun getListCategories(){
        viewModelScope.launch {
            repo.getAllCategories(true)
                .map {
                mapperCategoriesEntityToScan(it)
                }
                .collect {
                _listSpinner.value = it
            }
        }
    }

    private fun mapperCategoriesEntityToScan(item: List<CategoryEntity>): List<CategoryScan> {
     return item.map {
            CategoryScan(it.id, it.imageId, it.categoryName)
        }
    }

    private fun convertToCheckScan(check: String): CheckScan {
        //ТЕКСТ СКАНА = t=20210315T180100&s=234.60&fn=9960440300119563&i=7611&fp=3036044891&n=1
        val list = check.split("&")
        val dateCheck = "${list[0].subSequence(8, 10)}" +
                ".${list[0].subSequence(6, 8)}" +
                ".${list[0].subSequence(2, 6)}" +
                " ${list[0].subSequence(11, 13)}" +
                ":${list[0].subSequence(13, 15)}"
        val sum = list[1].substring(2).toDouble()
        val fn = list[2].substring(3).toLong()
        val i = list[3].substring(2).toLong()
        val fp = list[4].substring(3).toLong()
        return CheckScan(
            dateCheck = dateCheck,
            category = CategoryScan(0, 0, ""),
            sumCheck = sum,
            fnCheck = fn,
            iCheck = i,
            fpCheck = fp
        )
    }
}