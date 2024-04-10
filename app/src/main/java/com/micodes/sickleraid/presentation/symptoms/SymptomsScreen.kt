package com.micodes.sickleraid.presentation.symptoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.micodes.sickleraid.R
import com.micodes.sickleraid.presentation.common.composable.BasicButton
import com.micodes.sickleraid.presentation.common.composable.TopAppBarComposable
import com.micodes.sickleraid.presentation.symptoms.components.ElevatedChipButtons
import com.micodes.sickleraid.presentation.symptoms.components.SliderInput


@Composable
fun SymptomsScreen(
    viewModel: SymptomsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val chipItems = state.chipItems
    val selectedChipIndex = state.selectedChip
    val chipItem = chipItems[selectedChipIndex]
    val chipItemFrequencySliderValue = chipItem.frequencySliderValue
    val chipItemSeveritySliderValue = chipItem.severitySliderValue

    Scaffold(
        topBar = {
            TopAppBarComposable(
                title = {
                    Text(text = "Symptoms Tracking")
                },
                actions = listOf {
                    IconButton(onClick = { /* TODO */ }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_app),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 32.dp),
                    text = "How are you feeling today?",
                    style = MaterialTheme.typography.displaySmall
                )
                ElevatedChipButtons(
                    chips = chipItems,
                    selectedChip = selectedChipIndex,
                    modifier = Modifier.fillMaxWidth(),
                    onChipClick = { viewModel.onChipSelected(it) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                SliderInput(
                    title = "Frequency",
                    value = chipItemFrequencySliderValue,
                    onValueChange = {
                        viewModel.onFrequencySliderValueChanged(
                            it,
                            selectedChipIndex
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                SliderInput(
                    title = "Severity",
                    value = chipItemSeveritySliderValue,
                    onValueChange = {
                        viewModel.onSeveritySliderValueChanged(
                            it,
                            selectedChipIndex
                        )
                    }
                )
                Spacer(modifier = Modifier.height(48.dp))
                BasicButton(text = "SAVE", modifier = Modifier.fillMaxWidth()) {}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SymptomsScreenPreview() {
    SymptomsScreen()
}