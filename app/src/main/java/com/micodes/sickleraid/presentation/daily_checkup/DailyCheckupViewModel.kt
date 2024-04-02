package com.micodes.sickleraid.presentation.daily_checkup

import androidx.lifecycle.ViewModel
import com.micodes.sickleraid.domain.services.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DailyCheckupViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {
    private val _state = MutableStateFlow(DailyCheckupState())
    val state = _state.asStateFlow()

    fun onChangeTemperature(newValue: Int) = _state.update { it.copy(temperature = newValue) }
    fun onChangeWeight(newValue: Int) = _state.update { it.copy(weight = newValue) }
    fun onChangeSystolicBloodPressure(newValue: Int) = _state.update { it.copy(systolicBloodPressure = newValue) }
    fun onChangeDiastolicBloodPressure(newValue: Int) = _state.update { it.copy(diastolicBloodPressure = newValue) }
    fun onChangePulseRate(newValue: Int) = _state.update { it.copy(pulseRate = newValue) }
    fun onChangeRespiratoryRate(newValue: Int) = _state.update { it.copy(respiratoryRate = newValue) }


}
