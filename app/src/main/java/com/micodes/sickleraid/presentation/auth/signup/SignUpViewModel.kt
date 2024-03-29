package com.micodes.sickleraid.presentation.auth.signup

import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.micodes.sickleraid.data.remote.DataProvider
import com.micodes.sickleraid.domain.model.Response
import com.micodes.sickleraid.domain.services.AccountService
import com.micodes.sickleraid.presentation.common.ext.isValidEmail
import com.micodes.sickleraid.presentation.common.ext.isValidPassword
import com.micodes.sickleraid.presentation.common.ext.passwordMatches
import com.micodes.sickleraid.presentation.common.snackbar.SnackbarManager
import com.micodes.sickleraid.presentation.navgraph.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    val oneTapClient: SignInClient
) : ViewModel() {

//HERE
    val currentUser = getAuthState()
    init {
        // 2.
        getAuthState()
    }
    private fun getAuthState() = accountService.getAuthState(viewModelScope)

    fun signInAnonymously() = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.anonymousSignInResponse = Response.Loading
        DataProvider.anonymousSignInResponse = accountService.signInAnonymously()
    }

    fun signOut() = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.signOutResponse = Response.Loading
        DataProvider.signOutResponse = accountService.signOut()
    }

    fun oneTapSignIn() = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.oneTapSignInResponse = Response.Loading
        DataProvider.oneTapSignInResponse = accountService.onTapSignIn()
    }

    fun signInWithGoogle(credentials: SignInCredential) = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.googleSignInResponse = Response.Loading
        DataProvider.googleSignInResponse = accountService.signInWithGoogle(credentials)
    }

    //HERE
    var uiState = mutableStateOf(SignUpUiState())
        private set

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

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage("Email error")
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage("Password error")
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage("Password does not match")
            return
        }

        viewModelScope.launch {
            accountService.createUser(email, password)
            openAndPopUp(Screen.HomeScreen.route, Screen.SignUpScreen.route)

        }
    }
}