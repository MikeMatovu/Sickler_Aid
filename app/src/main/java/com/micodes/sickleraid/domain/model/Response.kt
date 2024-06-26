package com.micodes.sickleraid.domain.model

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.StateFlow

typealias FirebaseSignInResponse = Response<AuthResult>
typealias SignOutResponse = Response<Boolean>
typealias AuthStateResponse = StateFlow<FirebaseUser?>
typealias OneTapSignInResponse = Response<BeginSignInResult>
typealias DatabaseOperationResponse = Response<Boolean>

sealed class Response<out T> {
    object Loading: Response<Nothing>()
    data class Success<out T>(val data: T?): Response<T>()
    data class Failure(val e: Exception): Response<Nothing>()
}