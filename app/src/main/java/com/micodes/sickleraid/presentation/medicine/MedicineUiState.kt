package com.micodes.sickleraid.presentation.medicine

data class Medicine(
    val id: Int = 0,
    val medicineName: String,
    val dosage: String,
    val frequency: String,
    val taken: Boolean = false,
    val isEditMode: Boolean = false
)

data class MedicineUiState(
    var isLoading: Boolean = true,
    var openAlertDialog: Boolean = false,
    var medicineList: List<Medicine> = emptyList()
)



