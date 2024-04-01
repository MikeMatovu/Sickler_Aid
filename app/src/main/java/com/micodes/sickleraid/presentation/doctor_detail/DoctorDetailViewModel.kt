package com.micodes.sickleraid.presentation.doctor_detail

import androidx.lifecycle.ViewModel
import com.micodes.sickleraid.domain.services.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DoctorDetailViewModel @Inject constructor(
    private val accountService: AccountService,
): ViewModel() {

    private val _state = MutableStateFlow(DoctorDetailsState())
    val state = _state.asStateFlow()

    init {
        getDoctorInfo()
    }

    private fun getDoctorInfo() {
        _state.update { it.copy(profilePictureLink = "https://i.pinimg.com/564x/20/0f/50/200f509408e5ae122d1a45d110f2faa2.jpg")}
    }
}

