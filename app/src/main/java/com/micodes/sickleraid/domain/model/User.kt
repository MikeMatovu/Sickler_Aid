package com.micodes.sickleraid.domain.model

data class User(
    val id: String = "",
    val isAnonymous: Boolean = true,
    val email: String = "",
    val phoneNumber: String = "",
    val displayName: String = "",
    val photoUrl: String = "",
    val emailVerified: Boolean = false,
)