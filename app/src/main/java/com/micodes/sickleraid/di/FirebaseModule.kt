package com.micodes.sickleraid.di

import android.app.Application
import android.content.Context
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.micodes.sickleraid.R
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides fun auth(): FirebaseAuth = Firebase.auth

    @Provides fun firestore(): FirebaseFirestore = Firebase.firestore

    @Provides fun firebaseStorage(): FirebaseStorage = Firebase.storage

    @Provides
    fun provideOneTapClient(
        @ApplicationContext
        context: Context
    ) = Identity.getSignInClient(context)

    @Provides
    @Named("signInRequest")
    fun provideSignInRequest(
        app: Application
    ) = BeginSignInRequest.Builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest
                // 1.
                .GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                // 2.
                .setServerClientId(app.getString(R.string.web_client_id))
                // 3.
                .setFilterByAuthorizedAccounts(true)
                .build()
        )
        // 4.
        .setAutoSelectEnabled(true)
        .build()

    @Provides
    @Named("signUpRequest")
    fun provideSignUpRequest(
        app: Application
    ) = BeginSignInRequest.Builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest
                // 1.
                .GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                // 2.
                .setServerClientId(app.getString(R.string.web_client_id))
                // 5.
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()
}