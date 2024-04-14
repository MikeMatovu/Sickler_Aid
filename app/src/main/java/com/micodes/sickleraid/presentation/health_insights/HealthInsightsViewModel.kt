package com.micodes.sickleraid.presentation.health_insights

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.ui.barchart.models.BarChartType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.micodes.sickleraid.domain.repository.DailyCheckupRepository
import com.micodes.sickleraid.presentation.charts.util.getTemperatureBarChartData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
}
