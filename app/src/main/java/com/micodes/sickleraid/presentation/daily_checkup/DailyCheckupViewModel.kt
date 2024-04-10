package com.micodes.sickleraid.presentation.daily_checkup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.micodes.sickleraid.data.remote.DataProvider
import com.micodes.sickleraid.domain.model.DailyCheckup
import com.micodes.sickleraid.domain.model.Response
import com.micodes.sickleraid.domain.repository.DailyCheckupRepository
import com.micodes.sickleraid.domain.services.AccountService
import com.micodes.sickleraid.util.timestampToDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyCheckupViewModel @Inject constructor(
    private val accountService: AccountService,
    private val auth: FirebaseAuth,
    private val dailyCheckupRepository: DailyCheckupRepository
) : ViewModel() {
    private val _state = MutableStateFlow(DailyCheckupState())
    val state = _state.asStateFlow()


    init {
        getLastModified()
    }
    private fun getLastModified() {
        viewModelScope.launch {
            val currentUser: FirebaseUser? = auth.currentUser
            currentUser?.let { firebaseUser ->
                val userId = firebaseUser.uid
                val latestRecord = dailyCheckupRepository.getLatestCheckup(userId)
                latestRecord.let { record ->
                    // Convert timestamp to date and time
                    val dateTime = record?.let { timestampToDateTime(it.timestamp) }
                    _state.update { it.copy(lastModifiedDateTime = dateTime) }
                }
            }
        }

    }

    fun onChangeTemperature(newValue: Int) = _state.update { it.copy(temperature = newValue) }
    fun onChangeSystolicBloodPressure(newValue: Int) = _state.update { it.copy(systolicBloodPressure = newValue) }
    fun onChangeDiastolicBloodPressure(newValue: Int) = _state.update { it.copy(diastolicBloodPressure = newValue) }
    fun onChangePulseRate(newValue: Int) = _state.update { it.copy(pulseRate = newValue) }
    fun onChangeRespiratoryRate(newValue: Int) = _state.update { it.copy(respiratoryRate = newValue) }

    fun openDialog() = _state.update { it.copy(openAlertDialog = true) }
    fun onDialogDismiss()  {
        _state.update { it.copy(openAlertDialog = false) }
        DataProvider.databaseOperationResponse = Response.Success(null)
    }

    fun submitCheckup() {
        viewModelScope.launch {
            val currentUser: FirebaseUser? = auth.currentUser
            currentUser?.let { firebaseUser ->
                val currentState = state.value
                val checkup = DailyCheckup(
                    userId = firebaseUser.uid,
                    temperature = currentState.temperature,
                    systolicBP = currentState.systolicBloodPressure,
                    diastolicBP = currentState.diastolicBloodPressure,
                    pulseRate = currentState.pulseRate,
                    respiratoryRate = currentState.respiratoryRate
                )
                DataProvider.databaseOperationResponse = dailyCheckupRepository.insertDailyCheckup(checkup)
            }
        }

    }


}
