package com.micodes.sickleraid.data.remote

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseUser
import com.micodes.sickleraid.domain.model.FirebaseSignInResponse
import com.micodes.sickleraid.domain.model.OneTapSignInResponse
import com.micodes.sickleraid.domain.model.Response
import com.micodes.sickleraid.domain.model.SignOutResponse

enum class AuthState {
    Authenticated, // Anonymously authenticated in Firebase.
    SignedIn, // Authenticated in Firebase using one of service providers, and not anonymous.
    SignedOut; // Not authenticated in Firebase.
}

object DataProvider {

    // 1.
    var anonymousSignInResponse by mutableStateOf<FirebaseSignInResponse>(Response.Success(null))
    var googleSignInResponse by mutableStateOf<FirebaseSignInResponse>(Response.Success(null))
    var signOutResponse by mutableStateOf<SignOutResponse>(Response.Success(false))
    var oneTapSignInResponse by mutableStateOf<OneTapSignInResponse>(Response.Success(null))

    var user by mutableStateOf<FirebaseUser?>(null)

    var isAuthenticated by mutableStateOf(false)

    var isAnonymous by mutableStateOf(false)

    var authState by mutableStateOf(AuthState.SignedOut)
        private set

    // 2.
    fun updateAuthState(user: FirebaseUser?) {
        this.user = user
        isAuthenticated = user != null
        isAnonymous = user?.isAnonymous ?: false

        authState = if (isAuthenticated) {
            if (isAnonymous) AuthState.Authenticated else AuthState.SignedIn
        } else {
            AuthState.SignedOut
        }
    }
}