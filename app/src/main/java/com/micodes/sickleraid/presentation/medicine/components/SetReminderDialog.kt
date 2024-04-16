package com.micodes.sickleraid.presentation.medicine.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  SetReminderDialog () {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Select Reminder Time",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

//        TimePicker(
//            modifier = Modifier.fillMaxWidth()
//        )

        Spacer(modifier = Modifier.height(16.dp))

//        Button(
//            onClick = { onTimeSelected(selectedTime) },
//            modifier = Modifier.align(Alignment.End)
//        ) {
//            Text(text = "Set Reminder")
//        }
    }
}