package com.micodes.sickleraid.presentation.common.snackbar

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


object SnackbarManager {
    private val messages: MutableStateFlow<SnackbarMessage?> = MutableStateFlow(null)
    val snackbarMessages: StateFlow<SnackbarMessage?> = messages.asStateFlow()

    // Show String message
    fun showMessage(message: String) {
        messages.value = SnackbarMessage.StringSnackbar(message)
    }

    // Show SnackbarMessage
    fun showMessage(message: SnackbarMessage) {
        messages.value = message
    }

    // Clear Snackbar state
    fun clearSnackbarState() {
        messages.value = null
    }
}

