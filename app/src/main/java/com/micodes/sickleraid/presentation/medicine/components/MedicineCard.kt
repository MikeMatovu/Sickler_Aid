package com.micodes.sickleraid.presentation.medicine.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marosseleng.compose.material3.datetimepickers.time.domain.noSeconds
import com.marosseleng.compose.material3.datetimepickers.time.ui.dialog.TimePickerDialog
import com.micodes.sickleraid.presentation.common.composable.BasicButton
import com.micodes.sickleraid.presentation.common.composable.BasicTextButton
import java.time.LocalTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineCard(
    modifier: Modifier = Modifier,
    name: String,
    dosage: String,
    frequency: String,
    isEditMode: Boolean,
    onEditClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    onNameChanged: (String) -> Unit,
    onDosageChanged: (String) -> Unit,
    onFrequencyChanged: (String) -> Unit,
    onReminderSet: () -> Unit,
    onMarkAsTaken: () -> Unit
) {

    var isTimeDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    val (selectedTime, setSelectedTime) = rememberSaveable {
        mutableStateOf(LocalTime.now().noSeconds())
    }
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        if (isTimeDialogShown) {
            TimePickerDialog(
                onDismissRequest = {  },
                initialTime = selectedTime,
                onTimeChange = {
                    setSelectedTime(it)
                    isTimeDialogShown = false
                },
                title = { Text(text = "Select time") },
                buttonColors = ButtonDefaults.textButtonColors(contentColor = Color.Red),
            )
        }
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = name, style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(4.dp))

            if (isEditMode) {
                EditModeContent(
                    name = name,
                    dosage = dosage,
                    frequency = frequency,
                    onNameChanged = onNameChanged,
                    onDosageChanged = onDosageChanged,
                    onFrequencyChanged = onFrequencyChanged
                )
            } else {
                Text(
                    text = "Dosage: $dosage",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Frequency: $frequency",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (!isEditMode) {
                    BasicButton(
                        text = "Set Reminder",
                        modifier = Modifier,
                        action = {
                            isTimeDialogShown = true
                        }
                    )
                }

                Row(horizontalArrangement = Arrangement.End) {
                    if (!isEditMode) {
                        BasicTextButton(
                            text = "Mark as Taken",
                            modifier = Modifier,
                            action = {}
                        )
                        BasicTextButton(
                            text = "Edit",
                            modifier = Modifier,
                            action = onEditClicked
                        )
                    } else {
                        BasicTextButton(
                            text = "Save",
                            modifier = Modifier,
                            action = onSaveClicked
                        )
                        Spacer(modifier = modifier.weight(1f))
                        BasicTextButton(
                            text = "Cancel",
                            modifier = Modifier,
                            action = onCancelClicked
                        )
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun MedicineCardPreview() {
//    MedicineCard()
}