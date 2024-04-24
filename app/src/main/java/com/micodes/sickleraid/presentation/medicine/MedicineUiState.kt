package com.micodes.sickleraid.presentation.medicine

import com.marosseleng.compose.material3.datetimepickers.time.domain.noSeconds
import java.time.LocalTime

data class Medicine(
    val id: Int = 0,
    val medicineName: String,
    val dosage: String,
    val frequency: String,
    val taken: Boolean = false,
    val isEditMode: Boolean = false,
    val isTimeDialogShown: Boolean = false,
    val selectedTime: LocalTime = LocalTime.now().noSeconds()
)

data class MedicineUiState(
    val isLoading: Boolean = true,
    val openAlertDialog: Boolean = false,
    val medicineList: List<Medicine> = emptyList()
)



