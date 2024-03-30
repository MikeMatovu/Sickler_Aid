package com.micodes.sickleraid.domain.services

import com.google.android.gms.auth.api.identity.SignInCredential
import com.micodes.sickleraid.domain.model.AuthStateResponse
import com.micodes.sickleraid.domain.model.FirebaseSignInResponse
import com.micodes.sickleraid.domain.model.OneTapSignInResponse
import com.micodes.sickleraid.domain.model.SignOutResponse
import com.micodes.sickleraid.domain.model.User
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow


interface AccountService {
    val currentUserId: String
    val hasUser: Boolean

    val currentUser: Flow<User>

    suspend fun logInWithEmailAndPassword(email: String, password: String): FirebaseSignInResponse
    suspend fun sendRecoveryEmail(email: String)
    suspend fun signInAnonymously(): FirebaseSignInResponse
    suspend fun linkAccount(email: String, password: String)
    suspend fun createUser(email: String, password: String): FirebaseSignInResponse
    suspend fun deleteAccount()
    suspend fun signOut(): SignOutResponse

    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse
    suspend fun onTapSignIn(): OneTapSignInResponse
    suspend fun signInWithGoogle(credential: SignInCredential): FirebaseSignInResponse
}