package com.micodes.sickleraid.presentation.health_insights

import android.net.Uri
import co.yml.charts.ui.barchart.models.BarData
import com.micodes.sickleraid.data.remote.dto.PredictionResponse
import com.micodes.sickleraid.domain.model.DailyCheckup
import com.micodes.sickleraid.domain.model.LatestPatientRecords
import com.micodes.sickleraid.domain.model.TemperatureRecord

data class HealthInsightsState(
    val temperatureRecords: List<TemperatureRecord> = emptyList(),
    val dailyCheckups: List<DailyCheckup> = emptyList(),
    val barData: List<BarData> = emptyList(),
    val pdfUri: Uri? = null,
    val isLoading: Boolean = true,
    val isEmpty: Boolean = false,
    val error: Throwable? = null,
    val isRecommendationLoading: Boolean = true,
    val doctorRecommendation: String? = null,

    var predictionState: PredictionResponse = PredictionResponse("0", 1.0, 0.0),
    var latestRecords: LatestPatientRecords? = null,
    var isPredicting: Boolean = false
)