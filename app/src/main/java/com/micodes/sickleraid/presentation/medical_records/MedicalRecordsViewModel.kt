package com.micodes.sickleraid.presentation.medical_records

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.micodes.sickleraid.domain.model.MedicalRecords
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
    private val medicalRecordsRepository: MedicalRecordsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MedicalRecordsState())
    val state = _state.asStateFlow()


    init {
        getLastModified()
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

    fun saveRecords() {
        viewModelScope.launch {
            val currentUser: FirebaseUser? = auth.currentUser
            currentUser?.let { firebaseUser ->
                val userId = firebaseUser.uid
                Log.i("User ID", userId) // Log the user ID being used
                val records = MedicalRecords(
                    userId = userId,
                    bmi = state.value.bmi,
                    packetCellVolume = state.value.packetCellVolume,
                    platelets = state.value.platelets,
                    birulubin = state.value.birulubin,
                    ldh = state.value.lactateDehydrogenase,
                    fetalHaemoglobin = state.value.fetalHaemoglobin,
                    meanCorpuscularVolume = state.value.meanCorpuscularVolume,
                    aat = state.value.alanineAminotransferase,
                )
                medicalRecordsRepository.insertMedicalRecords(records)
            }
        }
    }


}


