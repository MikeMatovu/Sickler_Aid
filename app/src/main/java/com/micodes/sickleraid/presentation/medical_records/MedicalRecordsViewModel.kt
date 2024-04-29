package com.micodes.sickleraid.presentation.medical_records

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.micodes.sickleraid.data.remote.DataProvider
import com.micodes.sickleraid.domain.model.FirebaseMedicalRecord
import com.micodes.sickleraid.domain.model.MedicalRecords
import com.micodes.sickleraid.domain.model.Response
import com.micodes.sickleraid.domain.repository.FirebaseRepository
import com.micodes.sickleraid.domain.repository.MedicalRecordsRepository
import com.micodes.sickleraid.domain.services.AccountService
import com.micodes.sickleraid.util.timestampToDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MedicalRecordsViewModel @Inject constructor(
    private val accountService: AccountService,
    private val auth: FirebaseAuth,
    private val medicalRecordsRepository: MedicalRecordsRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MedicalRecordsState())
    val state = _state.asStateFlow()


    init {
        getLastModified()
        getLatestRecord()
    }

    private fun getLastModified() {
        viewModelScope.launch {
            val currentUser: FirebaseUser? = auth.currentUser
            currentUser?.let { firebaseUser ->
                val userId = firebaseUser.uid
                val latestRecord = medicalRecordsRepository.getLatestRecord(userId)
                latestRecord.let { medicalRecord ->
                    // Convert timestamp to date and time
                    val dateTime = medicalRecord?.let { timestampToDateTime(it.timestamp) }
                    _state.update { it.copy(lastModifiedDateTime = dateTime) }
                }
            }
        }

    }

    fun onChangeBMI(newValue: Int) = _state.update { it.copy(bmi = newValue) }
    fun onChangePacketCellVolume(newValue: Int) =
        _state.update { it.copy(packetCellVolume = newValue) }

    fun onChangeWeight(newValue: Int) =
        _state.update { it.copy(weight = newValue) }
    fun onChangePeripheralCapillarity(newValue: Int) =
        _state.update { it.copy(peripheralCapillarity = newValue) }

    fun onChangeFetalHaemoglobin(newValue: Int) =
        _state.update { it.copy(fetalHaemoglobin = newValue) }

    fun onChangeMeanCorpuscularVolume(newValue: Int) =
        _state.update { it.copy(meanCorpuscularVolume = newValue) }

    fun onChangeAlanineAminotransferase(newValue: Int) =
        _state.update { it.copy(alanineAminotransferase = newValue) }

    fun onChangeBirulubin(newValue: Int) = _state.update { it.copy(birulubin = newValue) }
    fun onChangeLactateDehydrogenase(newValue: Int) =
        _state.update { it.copy(lactateDehydrogenase = newValue) }

    fun onChangePlatelets(newValue: Int) = _state.update { it.copy(platelets = newValue) }

    fun onDialogConfirm() {
//        saveRecords()
        _state.update { it.copy(openAlertDialog = false) }
    }
    fun openDialog() = _state.update { it.copy(openAlertDialog = true) }
    fun onDialogDismiss()  {
        _state.update { it.copy(openAlertDialog = false) }
        DataProvider.databaseOperationResponse = Response.Success(null)
    }

    fun saveRecords() {
        viewModelScope.launch {
            DataProvider.databaseOperationResponse = Response.Loading
            val currentUser: FirebaseUser? = auth.currentUser
            currentUser?.let { firebaseUser ->
                val userId = firebaseUser.uid
                val records = MedicalRecords(
                    userId = userId,
                    bmi = state.value.bmi,
                    weight = state.value.weight,
                    packetCellVolume = state.value.packetCellVolume,
                    platelets = state.value.platelets,
                    birulubin = state.value.birulubin,
                    ldh = state.value.lactateDehydrogenase,
                    fetalHaemoglobin = state.value.fetalHaemoglobin,
                    meanCorpuscularVolume = state.value.meanCorpuscularVolume,
                    aat = state.value.alanineAminotransferase,
                )
                DataProvider.databaseOperationResponse = medicalRecordsRepository.insertMedicalRecords(records)
            }
        }
    }

    fun saveRecordsToFirebase() {
        viewModelScope.launch {
            DataProvider.databaseOperationResponse = Response.Loading
            val currentUser: FirebaseUser? = auth.currentUser
            currentUser?.let { firebaseUser ->
                val userId = firebaseUser.uid
                val firebaseMedicalRecord = FirebaseMedicalRecord(
                    userId = userId,
                    bmi = state.value.bmi,
                    weight = state.value.weight,
                    packetCellVolume = state.value.packetCellVolume,
                    peripheralCapacity = state.value.peripheralCapillarity,
                    platelets = state.value.platelets,
                    birulubin = state.value.birulubin,
                    ldh = state.value.lactateDehydrogenase,
                    fetalHaemoglobin = state.value.fetalHaemoglobin,
                    meanCorpuscularVolume = state.value.meanCorpuscularVolume,
                    aat = state.value.alanineAminotransferase,
                )
                DataProvider.databaseOperationResponse = firebaseRepository.createMedicalRecord(firebaseMedicalRecord)
            }
        }
    }

    fun getLatestRecord() {
        viewModelScope.launch {
            val currentUser: FirebaseUser? = auth.currentUser
            currentUser?.let { firebaseUser ->
                val userId = firebaseUser.uid
                val latestRecord = firebaseRepository.getLatestRecord(userId)
                latestRecord.let { medicalRecord ->
                    // Convert timestamp to date and time
                    val dateTime = medicalRecord?.let { timestampToDateTime(it.timestamp) }
                    _state.update { it.copy(lastModifiedDateTime = dateTime) }
                }
            }
        }
    }


}


