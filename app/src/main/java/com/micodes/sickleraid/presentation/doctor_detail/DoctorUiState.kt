package com.micodes.sickleraid.presentation.doctor_detail

import com.micodes.sickleraid.domain.model.Doctor

data class DoctorUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val profilePictureLink: String = "",
    val isInsertDoctorDialogVisible: Boolean = false,
    val isLoading: Boolean = true,
    val doctor: Doctor? = null,
    val doctorList: List<Doctor> = emptyList()
)