package com.micodes.sickleraid.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.micodes.sickleraid.data.remote.DataProvider
import com.micodes.sickleraid.domain.model.Response
import com.micodes.sickleraid.domain.services.AccountService
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
}