package com.micodes.sickleraid.presentation.doctor_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun OverViewSection() {
    Column {
        Text(
            text = "Overview",
            modifier = Modifier.padding(horizontal = 16.dp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            "This is the overview section of the doctor's profile. It contains a brief summary of the doctor's profile.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}