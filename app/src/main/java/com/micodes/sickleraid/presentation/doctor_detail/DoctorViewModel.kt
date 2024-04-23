package com.micodes.sickleraid.presentation.doctor_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.micodes.sickleraid.domain.model.Doctor
import com.micodes.sickleraid.domain.model.UserDoctorCrossRef
import com.micodes.sickleraid.domain.repository.DoctorRepository
import com.micodes.sickleraid.domain.services.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val accountService: AccountService,
    private val doctorRepository: DoctorRepository
): ViewModel() {

    private val _state = MutableStateFlow(DoctorUiState())
    val state = _state.asStateFlow()


    init {
        getDoctorsForUser()
//        getDoctorInfo()
//        fetchDoctors()
    }

    fun getDoctorById(doctorId: Int?) {
        viewModelScope.launch {
            doctorId?.let {
                val doctor = doctorRepository.getDoctor(it)
                _state.value = _state.value.copy(
                    doctor = doctor,
                    isLoading = false
                )
                Log.i("DoctorViewModel", "Doctor: ${_state.value.doctor}")
            }
        }
    }

    fun showDialog() {
        _state.value = _state.value.copy(isInsertDoctorDialogVisible = true)
    }

    fun hideDialog() {
        _state.value = _state.value.copy(isInsertDoctorDialogVisible = false)
    }
    fun onFirstNameChanged(firstName: String) {
        _state.update { it.copy(firstName = firstName) }
    }
    fun onLastNameChanged(lastName: String) {
        _state.update { it.copy(lastName = lastName) }
    }
    fun onEmailChanged(email: String) {
        _state.update { it.copy(email = email) }
    }
    fun onPhoneNumberChanged(phoneNumber: String) {
        _state.update { it.copy(phoneNumber = phoneNumber) }
    }
    private fun getDoctorInfo() {

        _state.update { it.copy(profilePictureLink = "https://i.pinimg.com/564x/20/0f/50/200f509408e5ae122d1a45d110f2faa2.jpg")}
    }
    fun insertDoctor() {
        viewModelScope.launch {
            val doctor = Doctor(
                id = 0,
                firstName = state.value.firstName,
                lastName = state.value.lastName,
                email = state.value.email,
                phoneNumber = state.value.phoneNumber
            )
            val insertedDoctorId = doctorRepository.upsertDoctor(doctor)
                val currentUser: FirebaseUser? = auth.currentUser
                currentUser?.let { firebaseUser ->
                    val userId = firebaseUser.uid
                    val userDoctorCrossRef = UserDoctorCrossRef(userId, insertedDoctorId.toInt())
                    doctorRepository.upsertUserAndDoctorCrossRef(userDoctorCrossRef)
            }
        }
    }

    private fun fetchDoctors() {
        viewModelScope.launch {
            val doctors = doctorRepository.getAllDoctors()
            Log.d("DoctorViewModel", "Doctors: $doctors")

        }
    }

    fun getDoctorsForUser() {
        viewModelScope.launch {
            val currentUser: FirebaseUser? = auth.currentUser
            currentUser?.let { firebaseUser ->
                val userId = firebaseUser.uid
                val doctors = doctorRepository.getDoctorsForUser(userId)
                _state.update { it.copy(doctorList = doctors)}
            }
        }
    }
}

