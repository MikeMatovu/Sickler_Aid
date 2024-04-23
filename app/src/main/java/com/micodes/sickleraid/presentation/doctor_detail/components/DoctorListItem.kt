package com.micodes.sickleraid.presentation.doctor_detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.micodes.sickleraid.R
import com.micodes.sickleraid.domain.model.Doctor

@Composable
fun DoctorListItem(
    doctor: Doctor,
    onDoctorDetailsClick: () -> Unit
) {


    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Doctor Profile Picture
            Image(
                painter = painterResource(R.drawable.resource_img_4),//TODO: Load doctor profile picture
                contentDescription = "Doctor Profile Picture",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Doctor Name
            Text(
                text = "${doctor.firstName} ${doctor.lastName}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            // Button
            Button(
                onClick = onDoctorDetailsClick,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(text = "Details")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DoctorListItemPreview() {
    DoctorListItem(
        doctor = Doctor(
            id = 1,
            firstName = "John",
            lastName = "Doe",
            email = "",
            phoneNumber = ""
        ),
        onDoctorDetailsClick = {}
    )
}

