package com.micodes.sickleraid.presentation.charts.util

import androidx.compose.ui.graphics.Color
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.models.BarChartType
import co.yml.charts.ui.barchart.models.BarData
import com.micodes.sickleraid.domain.model.TemperatureRecord
import java.text.SimpleDateFormat
import kotlin.random.Random
import java.util.*

fun getTemperatureBarChartData(
    temperatureRecords: List<TemperatureRecord>,
    barChartType: BarChartType,
    dataCategoryOptions: DataCategoryOptions
): List<BarData> {
    val list = arrayListOf<BarData>()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    temperatureRecords.forEachIndexed { index, record ->
        val date = dateFormat.format(Date(record.timestamp))
        val point = when (barChartType) {
            BarChartType.VERTICAL -> {
                Point(index.toFloat(), record.temperature.toFloat())
            }
            BarChartType.HORIZONTAL -> {
                Point(record.temperature.toFloat(), index.toFloat())
            }
        }

        list.add(
            BarData(
                point = point,
                color = Color(
                    Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)
                ),
                dataCategoryOptions = dataCategoryOptions,
                label = date
            )
        )
    }

    return list
}
