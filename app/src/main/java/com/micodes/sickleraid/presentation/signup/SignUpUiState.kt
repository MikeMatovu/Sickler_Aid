package com.micodes.sickleraid.presentation.signup

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)