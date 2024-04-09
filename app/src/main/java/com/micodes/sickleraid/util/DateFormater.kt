package com.micodes.sickleraid.util

import java.util.Date
import java.text.SimpleDateFormat

// Function to convert timestamp to date and time
fun timestampToDateTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = Date(timestamp)
    return sdf.format(date)
}
