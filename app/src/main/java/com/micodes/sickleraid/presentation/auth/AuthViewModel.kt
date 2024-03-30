package com.micodes.sickleraid.presentation.auth

import android.content.ContentValues.TAG
import android.util.Log
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val accountService: AccountService,
    val oneTapClient: SignInClient
) : ViewModel() {

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

    fun signInWithEmailAndPassword(email: String, password: String) = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.signInWithEmailResponse = Response.Loading
        DataProvider.signInWithEmailResponse = accountService.logInWithEmailAndPassword(email, password)
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
    fun createUserWithEmailAndPassword(email: String, password: String) = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.createUserResponse = Response.Loading
        DataProvider.createUserResponse = accountService.createUser(email, password)
    }
    fun validateCredentialsAndCreateAccount(email: String, password: String, repeatPassword: String) {
        if (!email.isValidEmail()) {
//            SnackbarManager.showMessage("Email error")
            Log.e(TAG, "Email Error")
            return
        }
        if (!password.isValidPassword()) {
//            SnackbarManager.showMessage("Password error")
            Log.e(TAG, "Password Error")
            return
        }

        if (!password.passwordMatches(repeatPassword)) {
//            SnackbarManager.showMessage("Password does not match")
            Log.e(TAG, "Password does not match")
            return
        }
        createUserWithEmailAndPassword(email, password)
    }

    fun validateCredentialsAndSignIn(email: String, password: String) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage("Email Error")
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage("Empty password error")
            return
        }
        signInWithEmailAndPassword(email, password)


    }
}