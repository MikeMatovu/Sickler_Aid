package com.micodes.sickleraid.presentation.health_insights

import co.yml.charts.ui.barchart.models.BarData
import com.micodes.sickleraid.domain.model.TemperatureRecord

data class HealthInsightsState(
    val temperatureRecords: List<TemperatureRecord> = emptyList(),
    val barData: List<BarData> = emptyList(),
    val isLoading: Boolean = true,
    val error: Throwable? = null
)