package com.example.weathernow.domain.utils

import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun ZonedDateTime.formatTo(format: String): String {
    return try {
        DateTimeFormatter.ofPattern(format).format(this)
    } catch (e: Exception) {
        ""
    }
}

fun String.getZonedDateTime(): ZonedDateTime {
    return ZonedDateTime.parse(
        this,
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault())
    )
}

fun String.getDateTimeFromString() : String {
    val mDate = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).parse(this)
    val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
    val year = yearFormat.format(mDate)

    val monthNameFormat = SimpleDateFormat("MMM", Locale.getDefault())
    val month = monthNameFormat.format(mDate)

    val dayOfTheMonthFormat = SimpleDateFormat("dd", Locale.getDefault())
    val day = dayOfTheMonthFormat.format(mDate)

    val dayOfTheWeekFormat = SimpleDateFormat("EEE", Locale.getDefault())
    val dayOfTheWeek = dayOfTheWeekFormat.format(mDate)

    return "$dayOfTheWeek, $day $month $year"
}

fun String.getDayOfTheWeek() : String? {
    val mDate = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).parse(this)
    val dayOfTheWeekFormat = SimpleDateFormat("EEE", Locale.getDefault())
    return dayOfTheWeekFormat.format(mDate)
}