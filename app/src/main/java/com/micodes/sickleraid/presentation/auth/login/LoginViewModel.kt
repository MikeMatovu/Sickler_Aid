package com.micodes.sickleraid.presentation.auth.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micodes.sickleraid.domain.services.AccountService
import com.micodes.sickleraid.presentation.common.ext.isValidEmail
import com.micodes.sickleraid.presentation.common.snackbar.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
) : ViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set
    val loginState: MutableLiveData<Boolean> = MutableLiveData()

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage("Email Error")
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage("Empty password error")
            return
        }

        viewModelScope.launch {
            accountService.authenticate(email, password)
            openAndPopUp("SETTINGS_SCREEN", "LOGIN_SCREEN")
        }
    }

    fun onForgotPasswordClick() {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage("Email error")
            return
        }

        viewModelScope.launch {
            accountService.sendRecoveryEmail(email)
            SnackbarManager.showMessage("Recovery email sent")
        }

    }
}