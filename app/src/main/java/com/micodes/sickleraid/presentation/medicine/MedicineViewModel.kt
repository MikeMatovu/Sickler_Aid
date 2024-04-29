package com.micodes.sickleraid.presentation.medicine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.micodes.sickleraid.data.remote.DataProvider
import com.micodes.sickleraid.domain.model.MedicineTable
import com.micodes.sickleraid.domain.model.Response
import com.micodes.sickleraid.domain.repository.MedicineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val repository: MedicineRepository,
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _state = MutableStateFlow(MedicineUiState())
    val state = _state.asStateFlow()

    private val _scrollToNewItem = MutableSharedFlow<Unit>()
    val scrollToNewItem: Flow<Unit> = _scrollToNewItem.asSharedFlow()

    val medicineListSize = state.value.medicineList.size
    init {
        getMedicineList()
    }


    fun openDialog() = _state.update { it.copy(openAlertDialog = true) }
    fun onDialogDismiss() {
        _state.update { it.copy(openAlertDialog = false) }
        DataProvider.databaseOperationResponse = Response.Success(null)
    }

    fun onReminderSet(index: Int, time: LocalTime) {
        _state.update {
            it.copy(
                medicineList = it.medicineList.mapIndexed { i, medicine ->
                    if (i == index) {
                        medicine.copy(
                            isTimeDialogShown = false,
                            selectedTime = time
                        )
                    } else {
                        medicine
                    }
                }
            )
        }
    }

    fun onCloseReminder(index: Int) {
        _state.update {
            it.copy(
                medicineList = it.medicineList.mapIndexed { i, medicine ->
                    if (i == index) {
                        medicine.copy(
                            isTimeDialogShown = false
                        )
                    } else {
                        medicine
                    }
                }
            )
        }
    }


    //TODO: REMOVE THIS FUNCTION
    fun onOpenReminder(index: Int) {
        _state.update {
            it.copy(
                medicineList = it.medicineList.mapIndexed { i, medicine ->
                    if (i == index) {
                        medicine.copy(
                            isTimeDialogShown = true
                        )
                    } else {
                        medicine
                    }
                }
            )
        }
    }

    fun onCancelClicked(index: Int) {
        _state.value = state.value.copy(
            medicineList = state.value.medicineList.mapIndexed { i, medicine ->
                if (i == index) {
                    medicine.copy(isEditMode = false)
                } else {
                    medicine
                }
            }
        )
        refresh()
    }

    fun onNameChanged(index: Int, name: String) {
        _state.value = state.value.copy(
            medicineList = state.value.medicineList.mapIndexed { i, medicine ->
                if (i == index) {
                    medicine.copy(medicineName = name)
                } else {
                    medicine
                }
            }
        )
    }

    fun onDosageChanged(index: Int, dosage: String) {
        _state.value = state.value.copy(
            medicineList = state.value.medicineList.mapIndexed { i, medicine ->
                if (i == index) {
                    medicine.copy(dosage = dosage)
                } else {
                    medicine
                }
            }
        )
    }

    fun onFrequencyChanged(index: Int, frequency: String) {
        _state.value = state.value.copy(
            medicineList = state.value.medicineList.mapIndexed { i, medicine ->
                if (i == index) {
                    medicine.copy(frequency = frequency)
                } else {
                    medicine
                }
            }
        )
    }

    fun createNewMedicine() {
        _state.value = state.value.copy(
            medicineList = state.value.medicineList + Medicine(
                medicineName = "",
                dosage = "",
                frequency = "",
                isEditMode = true
            )
        )
        viewModelScope.launch {
            _scrollToNewItem.emit(Unit)
        }
    }


    fun refresh() {
        _state.value = state.value.copy(
            medicineList = emptyList()
        )
        getMedicineList()
    }

    private fun getMedicineList() {
        viewModelScope.launch {
            val currentUser: FirebaseUser? = auth.currentUser
            currentUser?.let { firebaseUser ->
                val userId = firebaseUser.uid
                val medicineList = repository.getMedicines(userId)
                Log.i("MedicineViewModel", "MedicineList: $medicineList")
                _state.value = state.value.copy(
                    medicineList = medicineList.map {
                        Medicine(
                            id = it.id,
                            medicineName = it.name,
                            dosage = it.dosage,
                            frequency = it.frequency
                        )
                    },
                    isLoading = false
                )
            }
        }
    }

    fun onEditClicked(index: Int) {
        _state.value = state.value.copy(
            medicineList = state.value.medicineList.mapIndexed { i, medicine ->
                if (i == index) {
                    medicine.copy(isEditMode = true)
                } else {
                    medicine
                }
            }
        )

    }

    fun onSavedClicked(index: Int, id: Int) {
        DataProvider.databaseOperationResponse = Response.Loading
        viewModelScope.launch {
            val currentUser: FirebaseUser? = auth.currentUser
            currentUser?.let { firebaseUser ->
                val userId = firebaseUser.uid
                val medicine = MedicineTable(
                    id = id,
                    name = state.value.medicineList[index].medicineName,
                    dosage = state.value.medicineList[index].dosage,
                    frequency = state.value.medicineList[index].frequency,
                    userId = userId,
                )
                DataProvider.databaseOperationResponse = repository.insertMedicine(medicine)
            }

        }
        _state.value = state.value.copy(
            medicineList = state.value.medicineList.mapIndexed { i, medicine ->
                if (i == index) {
                    medicine.copy(isEditMode = false)
                } else {
                    medicine
                }
            }

        )

        refresh()
    }

}