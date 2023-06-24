package com.example.notesandroid.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Converter {
    fun convertTimestampToDateTime(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("d MMM yyyy HH:mm", Locale.getDefault())
        val date = Date(timestamp)
        return dateFormat.format(date)
    }
}