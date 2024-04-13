package com.micodes.sickleraid.presentation.medicine.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.micodes.sickleraid.presentation.common.composable.BasicButton
import com.micodes.sickleraid.presentation.common.composable.BasicTextButton

@Composable
fun MedicineCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(25.dp))
            .background(MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = "Medicine Name", style = MaterialTheme.typography.titleSmall)

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Dosage: 10mg", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Frequency: Twice a day", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                BasicButton(
                    text = "Set Reminder",
                    modifier = Modifier,
                    action = {}
                )

                Row(horizontalArrangement = Arrangement.End) {
                    BasicTextButton(
                        text = "Mark as Taken",
                        modifier = Modifier,
                        action = {}
                    )
                    BasicTextButton(
                        text = "Edit",
                        modifier = Modifier,
                        action = {}
                    )
                }

            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MedicineCardPreview() {
    MedicineCard()
}