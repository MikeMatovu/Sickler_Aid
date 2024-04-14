package com.micodes.sickleraid.presentation.medicine.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.micodes.sickleraid.presentation.common.composable.BasicField

@Composable
fun EditModeContent(
    name: String,
    dosage: String,
    frequency: String,
    onNameChanged: (String) -> Unit,
    onDosageChanged: (String) -> Unit,
    onFrequencyChanged: (String) -> Unit
) {
    Column {
        BasicField(
            modifier = Modifier.padding(8.dp),
            text = "Name",
            value = name,
            onNewValue = onNameChanged
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicField(
            modifier = Modifier.padding(8.dp),
            text = "Dosage",
            value = dosage,
            onNewValue = onDosageChanged
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicField(
            modifier = Modifier.padding(8.dp),
            text = "Frequency",
            value = frequency,
            onNewValue = onFrequencyChanged
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}
