package com.micodes.sickleraid.presentation.common.snackbar

import android.content.res.Resources


sealed class SnackbarMessage {
    data class StringSnackbar(val message: String) : SnackbarMessage()

    companion object {
        // Convert SnackbarMessage to actual message
        fun SnackbarMessage.toMessage(): String {
            return when (this) {
                is StringSnackbar -> message
            }
        }

        // Convert Throwable to SnackbarMessage
        fun Throwable.toSnackbarMessage(): SnackbarMessage {
            val message = this.message.orEmpty()
            return if (message.isNotBlank()) SnackbarMessage.StringSnackbar(message)
            else SnackbarMessage.StringSnackbar("This is the message") // Provide default message
        }
    }
}