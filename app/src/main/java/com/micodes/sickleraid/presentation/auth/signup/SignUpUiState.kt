package com.micodes.sickleraid.presentation.auth.signup

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)