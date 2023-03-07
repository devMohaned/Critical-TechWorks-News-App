package com.criticaltechworks.criticaltechworksnewsapp.core.util

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.beautifyDate(): String {
  /*  try {
       val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.getDefault())
        val targetFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault())
        val date: Date? = sdf.parse(this)
        val formattedDate: String = targetFormat.format(date) // 20120821

        return formattedDate
    } catch (unparsableDate: ParseException) {
        return "Wrong Date/Time Format"
    }*/
    // Maybe If I know the format of the dates, I could parse it, but since it has multiple types, no idea which format to parse
    return this
}

fun String.convertToDate(): Long {
    try {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.getDefault())
        val date: Date? = sdf.parse(this)
        val millis: Long? = date?.time
        return millis ?: 0L
    } catch (unparsableDate: ParseException) {
        return 0L
    }
}