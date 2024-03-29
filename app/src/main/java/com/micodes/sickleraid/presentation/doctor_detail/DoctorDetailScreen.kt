package com.micodes.sickleraid.presentation.doctor_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DoctorDetailsScreen() {
    Column() {

        Icon(imageVector = Icons.Default.BrokenImage, contentDescription = null)
        Text("Text")
        Text("Text")
        Row() {
            Button(onClick = {}) {
                Text(text = "Click")
            }
            Button(onClick = {}) {
                Text(text = "Click")
            }
        }

        Column() {

            Text("Overview")
            Text("")

        }

        Button(onClick = {}) {
            Text(text = "Schedule Now")
        }

    }
}