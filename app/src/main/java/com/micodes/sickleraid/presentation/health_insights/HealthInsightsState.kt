package com.micodes.sickleraid.presentation.health_insights

import android.net.Uri
import co.yml.charts.ui.barchart.models.BarData
import com.micodes.sickleraid.domain.model.TemperatureRecord

data class HealthInsightsState(
    val temperatureRecords: List<TemperatureRecord> = emptyList(),
    val barData: List<BarData> = emptyList(),
    val pdfUri: Uri? = null,
    val isLoading: Boolean = true,
    val isEmpty: Boolean = false,
    val error: Throwable? = null
)