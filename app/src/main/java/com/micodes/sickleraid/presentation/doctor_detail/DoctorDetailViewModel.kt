package com.micodes.sickleraid.presentation.doctor_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micodes.sickleraid.data.datasource.database.SicklerAidDao
import com.micodes.sickleraid.domain.model.Doctor
import com.micodes.sickleraid.domain.repository.DoctorRepository
import com.micodes.sickleraid.domain.repository.SicklerAidRepository
import com.micodes.sickleraid.domain.services.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorDetailViewModel @Inject constructor(
    private val accountService: AccountService,
    private val doctorRepository: DoctorRepository
): ViewModel() {

    private val _state = MutableStateFlow(DoctorDetailsState())
    val state = _state.asStateFlow()


    init {
        getDoctorInfo()
        fetchDoctors()
    }


    private fun getDoctorInfo() {

        _state.update { it.copy(profilePictureLink = "https://i.pinimg.com/564x/20/0f/50/200f509408e5ae122d1a45d110f2faa2.jpg")}
    }
    fun insertDoctor() {
        viewModelScope.launch {
            val doctor = Doctor(0, "John", "Doe", "john.doe@example.com", "1234567890")
            doctorRepository.upsertDoctor(doctor)
            Log.d("DoctorDetailViewModel", "Doctor inserted successfully")
        }
    }

    private fun fetchDoctors() {
        viewModelScope.launch {
            val doctors = doctorRepository.getAllDoctors()
            Log.d("DoctorDetailViewModel", "Doctors: $doctors")

        }
    }
}

