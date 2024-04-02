package com.micodes.sickleraid.presentation.daily_checkup

data class DailyCheckupState(
    val temperature: Int = 0,
    val weight: Int = 0,
    val systolicBloodPressure: Int = 0,
    val diastolicBloodPressure: Int = 0,
    val pulseRate: Int = 0,
    val respiratoryRate: Int = 0
)