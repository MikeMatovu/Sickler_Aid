package com.micodes.sickleraid.presentation.profile

data class ProfileUiState(
    val firstName: String = "",
    val lastName: String = "",
    val location: String = "",
    val email: String = "",
    val phone: String = "",
    val profilePictureLink: String = "",
    val openAlertDialog: Boolean = false,
)