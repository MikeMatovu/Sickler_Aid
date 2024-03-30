package com.micodes.sickleraid.presentation.profile


import androidx.lifecycle.ViewModel
import com.micodes.sickleraid.domain.services.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountService: AccountService,
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    init {
        getUserInfo()
    }

    private fun getUserInfo() =
        _state.update { it.copy(profilePictureLink = "https://i.pinimg.com/564x/20/0f/50/200f509408e5ae122d1a45d110f2faa2.jpg") }

    fun onChangeFirstName(newValue: String) = _state.update { it.copy(firstName = newValue) }

    fun onChangeLastName(newValue: String) = _state.update { it.copy(lastName = newValue) }

    fun onChangeLocation(newValue: String) = _state.update { it.copy(location = newValue) }

    fun onChangeEmail(newValue: String) = _state.update { it.copy(email = newValue) }

    fun onChangePhone(newValue: String) = _state.update { it.copy(phone = newValue) }

    fun onSaveUserInfo() {
        TODO()
    }
}