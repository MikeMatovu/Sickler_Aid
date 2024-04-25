package com.micodes.sickleraid.presentation.doctor_detail

import android.net.Uri
import com.micodes.sickleraid.domain.model.Doctor
import com.micodes.sickleraid.domain.model.FirebaseDoctor

data class DoctorUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val phoneNumberToDial: String = "",
    val shouldDialPhoneNumber: Boolean = false,
    val selectedFileUri: Uri? = null,
    val isDateDialogShown: Boolean = false,
    val profilePictureLink: String = "",
    val isInsertDoctorDialogVisible: Boolean = false,
    val isLoading: Boolean = true,
    val doctor: Doctor? = null,
    val firebaseDoctor: FirebaseDoctor? = null,
    val doctorList: List<Doctor> = emptyList(),
    val isDoctorLoading: Boolean = true,
    val firebaseDoctorList: List<FirebaseDoctor> = emptyList()
)