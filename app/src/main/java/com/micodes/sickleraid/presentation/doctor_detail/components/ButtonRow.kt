package com.micodes.sickleraid.presentation.doctor_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonRow(
    onAppointmentClick: () -> Unit,
    onSendReportClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        FilledTonalButton(
            onClick = onAppointmentClick,
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(Icons.Filled.StarRate, contentDescription = "Book")
                Spacer(modifier = Modifier.padding(horizontal = 2.dp)) //Spacing between icon and text
                Text("Appointment")
            }
        }

        Spacer(modifier = Modifier.width(16.dp))
        FilledTonalButton(
            onClick = onSendReportClick,
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            )
        ) {
            Text("Send Report")
        }

    }

}