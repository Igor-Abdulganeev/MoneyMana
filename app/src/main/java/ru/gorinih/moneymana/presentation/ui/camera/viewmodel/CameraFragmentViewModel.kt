package ru.gorinih.moneymana.presentation.ui.camera.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import ru.gorinih.moneymana.data.model.CategoryEntity
import ru.gorinih.moneymana.data.model.CheckEntity
import ru.gorinih.moneymana.domain.CategoriesRepository
import ru.gorinih.moneymana.domain.CheckRepository
import ru.gorinih.moneymana.presentation.model.CategoryScan
import ru.gorinih.moneymana.presentation.model.CheckScan
import ru.gorinih.moneymana.presentation.model.ResultScan
import ru.gorinih.moneymana.utils.DateTimeConverter

class CameraFragmentViewModel(
    private val repoCategory: CategoriesRepository,
    private val repoCheck: CheckRepository
) : ViewModel() {

    private var _listSpinner = MutableLiveData<List<CategoryScan>>()
    val listSpinner: LiveData<List<CategoryScan>>
        get() = _listSpinner

    private val dateTime = DateTimeConverter()

    fun checkScan(scan: String): ResultScan {
        return try {
            val result = convertToCheckScan(scan)
            setNewCheck(result)
            ResultScan(result.dateCheck, result.sumCheck.toString())
        } catch (ex: Throwable) {
            ResultScan("", "")
        }
    }

    @InternalCoroutinesApi
    fun getListCategories() {
        viewModelScope.launch {
            repoCategory.getAllCategoriesIsActive(true)
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

    suspend fun addNewCheck(categoryCheck: CategoryScan, date: String, sum: String): Boolean {
        val check: CheckEntity
        if (newCheck.dateCheck.isEmpty() || newCheck.dateCheck != date) {
            check = CheckEntity(
                id = null,
                idCategory = categoryCheck.id ?: 1,
                dayCheck = dateTime.setDateStringToLong(date.substring(0, 10)),
                dateCheck = dateTime.setDateTimeStringToLong(date),
                sumCheck = sum.toDouble(),
                fnCheck = 0L,
                iCheck = 0L,
                fpCheck = 0L
            )
        } else {
            check = CheckEntity(
                id = null,
                idCategory = categoryCheck.id ?: 1,
                dayCheck = dateTime.setDateStringToLong(newCheck.dateCheck.substring(0, 10)),
                dateCheck = dateTime.setDateTimeStringToLong(newCheck.dateCheck),
                sumCheck = newCheck.sumCheck,
                fnCheck = newCheck.fnCheck,
                iCheck = newCheck.iCheck,
                fpCheck = newCheck.fpCheck
            )
        }

        return insertCheck(check)
    }

    private suspend fun insertCheck(check: CheckEntity): Boolean {
        val job = viewModelScope.async(Dispatchers.IO) {
            repoCheck.insertCheck(check)
        }
        return job.await()
    }

    private fun setNewCheck(scanCheck: CheckScan) {
        newCheck = scanCheck
    }

    fun getCurrentDate() = dateTime.setCurrentDateTimeToString()

    companion object {
        private var newCheck = CheckScan("", CategoryScan(0, 0, ""), 0.0, 0, 0, 0)
    }
}