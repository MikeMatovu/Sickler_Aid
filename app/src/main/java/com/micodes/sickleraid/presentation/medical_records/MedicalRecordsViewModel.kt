package com.micodes.sickleraid.presentation.medical_records

import androidx.lifecycle.ViewModel
import com.micodes.sickleraid.domain.services.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class MedicalRecordsViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {
    private val _state = MutableStateFlow(MedicalRecordsState())
    val state = _state.asStateFlow()

    fun onChangeBMI(newValue: Int) = _state.update { it.copy(bmi = newValue) }
    fun onChangePacketCellVolume(newValue: Int) = _state.update { it.copy(packetCellVolume = newValue) }
    fun onChangePeripheralCapillarity(newValue: Int) = _state.update { it.copy(peripheralCapillarity = newValue) }
    fun onChangeFetalHaemoglobin(newValue: Int) = _state.update { it.copy(fetalHaemoglobin = newValue) }
    fun onChangeMeanCorpuscularVolume(newValue: Int) = _state.update { it.copy(meanCorpuscularVolume = newValue) }
    fun onChangeAlanineAminotransferase(newValue: Int) = _state.update { it.copy(alanineAminotransferase = newValue) }
    fun onChangeBirulubin(newValue: Int) = _state.update { it.copy(birulubin = newValue) }
    fun onChangeLactateDehydrogenase(newValue: Int) = _state.update { it.copy(lactateDehydrogenase = newValue) }
    fun onChangePlatelets(newValue: Int) = _state.update { it.copy(platelets = newValue) }



}