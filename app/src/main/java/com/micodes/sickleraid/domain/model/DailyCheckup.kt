package com.micodes.sickleraid.domain.model

import android.health.connect.datatypes.BasalBodyTemperatureRecord
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_checkup")
data class DailyCheckup (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String = "",
    val temperature: Int = 0,
    val systolicBP: Int = 0,
    val diastolicBP: Int = 0,
    val pulseRate: Int = 0,
    val respiratoryRate: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)