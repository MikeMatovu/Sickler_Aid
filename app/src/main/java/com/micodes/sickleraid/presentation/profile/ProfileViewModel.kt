package com.micodes.sickleraid.presentation.profile


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import com.micodes.sickleraid.data.remote.DataProvider
import com.micodes.sickleraid.domain.model.Response
import com.micodes.sickleraid.domain.model.UserDetails
import com.micodes.sickleraid.domain.repository.UserRepository
import com.micodes.sickleraid.domain.services.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val auth: FirebaseAuth,
    private val userRepository: UserRepository
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

    private fun updateFirebaseDisplayName(firstName: String, lastName: String) {
        val currentUser: FirebaseUser? = auth.currentUser
        currentUser?.updateProfile(userProfileChangeRequest {
            displayName = "$firstName $lastName"
        })?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("UpdateProfileViewModel", "User display name updated successfully.")
            } else {
                Log.e("UpdateProfileViewModel", "User display name update failed.")
            }
        }
    }

    suspend fun getUserDetails(): UserDetails? {
        return viewModelScope.async {
            val currentUserId = accountService.currentUserId
            userRepository.getUser(currentUserId)
        }.await()
    }

    fun onSaveUserInfo() {
        viewModelScope.launch {
            DataProvider.databaseOperationResponse = Response.Loading
            val userUpdate = UserDetails(
                firstName = state.value.firstName,
                lastName = state.value.lastName,
                phoneNumber = state.value.phone,
            )
            userUpdate.firstName?.let {
                userUpdate.lastName?.let { it1 ->
                    updateFirebaseDisplayName(
                        it,
                        it1
                    )
                }
            }
            DataProvider.databaseOperationResponse = userRepository.saveUserDetailsToDatabase(userUpdate)
        }

    }
}