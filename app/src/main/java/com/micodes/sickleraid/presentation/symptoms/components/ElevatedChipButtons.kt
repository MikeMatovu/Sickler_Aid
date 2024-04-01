package com.micodes.sickleraid.presentation.symptoms.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ElevatedChipButtons(
    modifier: Modifier,
    chips: List<ChipData>,
    selectedChip: Int,
    onChipClick: (Int) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        chips.forEach { chipItem ->
            ElevatedFilterChip(
                selected = chipItem.isSelected,
                onClick = { },
                label = {
                    Text(text = chipItem.label)
                },
                colors = FilterChipDefaults.elevatedFilterChipColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.tertiary
                ),
                shape = CircleShape
            )
        }
    }


}


data class ChipData(
    val label: String,
    val isSelected: Boolean,
)