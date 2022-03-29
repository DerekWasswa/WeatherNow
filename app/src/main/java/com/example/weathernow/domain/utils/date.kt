package com.example.weathernow.domain.utils

import java.sql.Timestamp
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun ZonedDateTime.formatTo(format: String): String {
    return try {
        DateTimeFormatter.ofPattern(format).format(this)
    } catch (e: Exception) {
        ""
    }
}

fun String.getZonedDateTime(): ZonedDateTime {
    val timestamp = Timestamp.valueOf(this)
    val instant = timestamp.toInstant()
    return instant.atZone(ZoneId.systemDefault())
}