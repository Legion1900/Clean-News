package com.legion1900.cleannews.data.impl.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    fun dateToFormatStr(date: Date, format: String): String {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        return dateFormat.format(date)
    }

    fun getCurrentDate(): Date = Calendar.getInstance().time

    /*
    * Subtracts second date from first.
    * */
    fun subtract(first: Date, second: Date): Long {
        return first.time - second.time
    }
}