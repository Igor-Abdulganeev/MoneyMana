package ru.gorinih.moneymana.utils

import android.os.Build
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

/**
 *  Для работы с датами
 *    setCurrentDateTimeToString() - получает текущую дату и возвращает строку вида
 *     "21.03.2021 23:11"
 *
 *    setDateTimeStringToLong() - из строки вида "21.03.2021 23:11" формирует значение даты
 *    в формате Long для работы с базой данных
 *
 *   setDateTimeStringFromLong() - из переменной Long возвращает строку вида
 *     "21.03.2021 23:11"
 *
 *     getFirstDay() и getLastDay() - возвращают в формате Long соответственно первый и последний дни
 *     текущего месяца
 * */
class DateTimeConverter {
    fun setCurrentDateTimeToString(): String =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val currentDate = Date()
            SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(currentDate)
        } else {
            val formatDateTime =
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.getDefault())
            LocalDateTime.now(ZoneId.systemDefault()).format(formatDateTime)
        }

    fun setDateTimeStringToLong(dateTime: String): Long =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val dateFormat: Date =
                SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).parse(dateTime)!!
            dateFormat.time.div(1000)
        } else {
            val format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.getDefault())
            LocalDateTime.parse(dateTime, format).toEpochSecond(ZoneOffset.UTC)
        }

    fun setDateStringToLong(date: String): Long =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val dateFormat: Date =
                SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(date)!!
            dateFormat.time.div(1000)
        } else {
            val format = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault())
            LocalDate.parse(date, format).toEpochDay()
            //LocalDateTime.parse().toEpochSecond(ZoneOffset.UTC)
        }

    fun setDateTimeLongToString(dateTime: Long): String =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val dateTimeLong = Date(dateTime * 1000)
            SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(dateTimeLong)
        } else {
            val formatDateTime =
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.getDefault())
            LocalDateTime.ofEpochSecond(dateTime, 0, ZoneOffset.UTC).format(formatDateTime)
        }

    enum class DayType(val type: Int) {
        FirstDay(0),
        LastDay(1)
    }

    fun getFirstDay(): Long = getFindingDay(DayType.FirstDay)

    fun getLastDay(): Long = getFindingDay(DayType.LastDay)

    private fun getFindingDay(type: DayType): Long {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val nowCalendar = Calendar.getInstance()
            nowCalendar.add(Calendar.MONTH, 0)
            val finding = when(type){
                DayType.FirstDay -> nowCalendar.getActualMinimum(Calendar.DAY_OF_MONTH)
                DayType.LastDay -> nowCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            }
            nowCalendar.set(Calendar.DATE, finding)
            val oldDateFormat = SimpleDateFormat(
                "dd.MM.yyyy",
                Locale.getDefault()
            ).format(nowCalendar.time)
            val newDateFormat = SimpleDateFormat(
                "dd.MM.yyyy",
                Locale.getDefault()
            ).parse(oldDateFormat)
            newDateFormat?.time?.div(1000) ?: 0L
        } else {
            val nowDate = LocalDate.now()
            val finding = when(type){
                DayType.FirstDay -> 1
                DayType.LastDay -> nowDate.lengthOfMonth()
            }
            val firstDay = nowDate.withDayOfMonth(finding)
            firstDay.toEpochDay()
        }
    }

}