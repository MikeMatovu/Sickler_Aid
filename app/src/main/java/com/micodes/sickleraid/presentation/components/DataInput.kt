package com.micodes.sickleraid.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DataInput() {

    Column() {
        Text("Symptom Tracking")

        Text("Symptom type")
        Row() {}
        Row() {}
        Row() {}

        Row() {
            Text("")
            //Slider Menu
        }
        Text("Severity")

        Row() {
            Column() {
                //icon
                Text("Mild")
            }
            Column() {
                //icon
                Text("Moderate")
            }
            Column() {
                //icon
                Text("Severe")
            }
            Column() {
                //icon
                Text("Critical")
            }

        }

        Button(onClick = {}) {
            Text(text = "Save")

        }

    }


}

