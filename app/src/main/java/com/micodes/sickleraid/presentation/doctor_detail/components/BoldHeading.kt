package com.micodes.sickleraid.presentation.doctor_detail.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun BoldHeading() {
    Text(
        text = "Lorem Ipsum Et Cetera",
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.headlineMedium
    )
}