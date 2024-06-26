package com.micodes.sickleraid.data.datasource.database

import androidx.room.TypeConverter
import java.util.Date

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date {
        return value?.let { Date(it) } ?: Date(System.currentTimeMillis())
    }

    @TypeConverter
    fun toTimestamp(value: Date?): Long {
        return value?.let { value.time } ?: System.currentTimeMillis()
    }
}