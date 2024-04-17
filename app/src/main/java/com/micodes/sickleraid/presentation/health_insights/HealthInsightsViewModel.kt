package com.micodes.sickleraid.presentation.health_insights

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.ui.barchart.models.BarChartType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.micodes.sickleraid.domain.repository.DailyCheckupRepository
import com.micodes.sickleraid.presentation.charts.util.getTemperatureBarChartData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
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
    }

    fun updatePdfUri(uri: Uri?) {
        _state.value = _state.value.copy(pdfUri = uri)
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
                        add(Paragraph("Report Title"))
                        add(Paragraph("Report Content"))
                        close() // Explicitly close the document
                    }
                }
            } catch (e: ExceptionInInitializerError) {
                Log.e("PDF", "Exception when initializing PdfDocument", e)
            }
        }

        pdfUri
    }
}
