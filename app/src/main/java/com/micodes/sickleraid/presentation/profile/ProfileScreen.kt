package com.micodes.sickleraid.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Label
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(

) {

    var name by remember { mutableStateOf(TextFieldValue()) }
    var dob by remember { mutableStateOf(TextFieldValue()) }
    var location by remember { mutableStateOf(TextFieldValue()) }
    var email by remember { mutableStateOf(TextFieldValue()) }
    var contactNumber by remember { mutableStateOf(TextFieldValue()) }

    Column {

        Text(text = "Complete your health profile")

        Icon(imageVector = Icons.Default.BrokenImage, contentDescription = null)

        Text("Add persa")

        TextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Full name") })

        TextField(
            value = dob,
            onValueChange = { dob = it },
            placeholder = { Text("Date of birth") })

        TextField(
            value = location,
            onValueChange = { location = it },
            placeholder = { Text("Location Information") })

        TextField(value = email, onValueChange = { email = it }, placeholder = { Text("Email") })

        TextField(
            value = contactNumber,
            onValueChange = { contactNumber = it },
            placeholder = { Text("Contact number") })

//        FileArea(){
//
//        }

        Button(onClick = {}) {
            Text(text = "Click")
        }

    }


}