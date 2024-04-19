package com.micodes.sickleraid.presentation.health_insights

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.barchart.models.BarChartType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import com.micodes.sickleraid.R
import com.micodes.sickleraid.domain.model.DailyCheckup
import com.micodes.sickleraid.domain.repository.DailyCheckupRepository
import com.micodes.sickleraid.presentation.charts.BarchartWithSolidBars
import com.micodes.sickleraid.presentation.charts.util.getTemperatureBarChartData
import com.micodes.sickleraid.util.getCurrentDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class HealthInsightsViewModel @Inject constructor(
    private val dailyCheckupRepository: DailyCheckupRepository,
    private val auth: FirebaseAuth,
) : ViewModel() {
    private val _state = MutableStateFlow(HealthInsightsState())
    val state = _state.asStateFlow()


    init {
        getTemperatureRecords()
    }

    fun refreshData() {
        getTemperatureRecords()
        getAllDailyCheckup()
        generateRandomPrediction()
    }

    fun updatePdfUri(uri: Uri?) {
        _state.value = _state.value.copy(pdfUri = uri)
    }


    private fun generateRandomPrediction() {
        //Increase the value every 3 seconds until max
        viewModelScope.launch {
            var prediction = 0.0f
            while (prediction < 1.0f) {
                _state.value = _state.value.copy(prediction = prediction)
                prediction += 0.1f
                kotlinx.coroutines.delay(3000)
            }
        }
    }

    private fun getTemperatureRecords() {
        viewModelScope.launch {
            _state.value =
                state.value.copy(isLoading = true) // Set isLoading to true before loading data

            val currentUser: FirebaseUser? = auth.currentUser
            currentUser?.let { firebaseUser ->
                val userId = firebaseUser.uid
                try {
                    val temperatureRecords = dailyCheckupRepository.getTemperatureRecords(userId)
                    if (temperatureRecords.isEmpty()) {
                        _state.value = state.value.copy(
                            isEmpty = true,
                            isLoading = false // Set isLoading to false after loading data
                        )
                    } else {
                        _state.value = state.value.copy(
                            temperatureRecords = temperatureRecords,
                            barData = getTemperatureBarChartData(
                                temperatureRecords,
                                BarChartType.VERTICAL,
                                DataCategoryOptions()
                            ),
                            isLoading = false // Set isLoading to false after loading data
                        )
                    }
                } catch (e: Exception) {
                    _state.value = state.value.copy(
                        error = e,
                        isLoading = false
                    ) // Set isLoading to false if an error occurs
                }
            }
        }
    }


    private fun getAllDailyCheckup() {
        viewModelScope.launch {
            val currentUser: FirebaseUser? = auth.currentUser
            currentUser?.let { firebaseUser ->
                val userId = firebaseUser.uid
                //TODO: handle empty case
                try {
                    val dailyCheckups = dailyCheckupRepository.getAllCheckups(userId)
                    _state.value = state.value.copy(dailyCheckups = dailyCheckups)
                    Log.i("DailyCheckups", dailyCheckups.toString())
                } catch (e: Exception) {
                    _state.value = state.value.copy(error = e)
                }

            }
        }
    }

    suspend fun generatePdfReport(context: Context): Uri? = withContext(Dispatchers.IO) {
        val fileName = "report.pdf"
        val pdfUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            File(context.cacheDir, fileName)
        )

        context.contentResolver.openOutputStream(pdfUri)?.use { outputStream ->
            try {
                PdfDocument(PdfWriter(outputStream)).use { pdf ->
                    Document(pdf).apply {

                        val bitmap =
                            BitmapFactory.decodeResource(context.resources, R.drawable.cells)
                        val file = File(context.cacheDir, "cells.jpg")
                        val fos = FileOutputStream(file)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                        fos.close()
                        val imageData = ImageDataFactory.create(file.absolutePath)
                        val image = Image(imageData)
                        add(image.scaleAbsolute(100f, 100f).apply {
                            setFixedPosition(40f, 750f)
                        })


                        add(Paragraph("Daily checkup report").apply {
                            setTextAlignment(TextAlignment.CENTER)
                            setMarginTop(20f)
                            setMarginBottom(20f)
                        })
                        add(Paragraph("Generated on: ${getCurrentDateTime()}").apply {
                            setTextAlignment(TextAlignment.RIGHT)
                            setMarginTop(20f)
                            setMarginBottom(20f)
                        })
                        add(createTable(state.value.dailyCheckups))
                        close() // Explicitly close the document
                    }
                }
            } catch (e: ExceptionInInitializerError) {
                Log.e("PDF", "Exception when initializing PdfDocument", e)
            }
        }

        pdfUri
    }


    private fun createTable(dailyCheckups: List<DailyCheckup>): Table {
        val table = Table(UnitValue.createPercentArray(floatArrayOf(10f, 10f, 20f, 20f, 20f, 20f)))
            .useAllAvailableWidth()

        table.addCell(Paragraph("ID"))
        table.addCell(Paragraph("Temperature"))
        table.addCell(Paragraph("Systolic BP"))
        table.addCell(Paragraph("Diastolic BP"))
        table.addCell(Paragraph("Pulse Rate"))
        table.addCell(Paragraph("Respiratory Rate"))

        dailyCheckups.forEach { checkup ->
            table.addCell(Paragraph(checkup.id.toString()))
            table.addCell(Paragraph(checkup.temperature.toString()))
            table.addCell(Paragraph(checkup.systolicBP.toString()))
            table.addCell(Paragraph(checkup.diastolicBP.toString()))
            table.addCell(Paragraph(checkup.pulseRate.toString()))
            table.addCell(Paragraph(checkup.respiratoryRate.toString()))
        }

        return table
    }
}



