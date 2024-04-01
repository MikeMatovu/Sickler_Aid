package com.micodes.sickleraid.presentation.symptoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.micodes.sickleraid.R
import com.micodes.sickleraid.presentation.common.composable.BasicButton
import com.micodes.sickleraid.presentation.common.composable.TopAppBarComposable
import com.micodes.sickleraid.presentation.symptoms.components.ChipData
import com.micodes.sickleraid.presentation.symptoms.components.ElevatedChipButtons
import com.micodes.sickleraid.presentation.symptoms.components.SliderInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymptomsScreen(
//    viewModel: SymptomsViewModel = hiltViewModel()
) {

    val chipItems = remember {
        listOf(
            ChipData(label = "Headache", false),
            ChipData(label = "Pain", false),
            ChipData(label = "Fatigue", true),
            ChipData(label = "Nausea", false),
            ChipData(label = "Fever", false),
            ChipData(label = "Shortness of Breath", false),
            ChipData(label = "Chest pain", false),
            ChipData(label = "Anxiety", false),
            ChipData(label = "Depression", false),
        )
    }
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
            modifier = Modifier.padding(paddingValues)
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
                    selectedChip = 1,
                    modifier = Modifier.fillMaxWidth(),
                    onChipClick = {}
                )
                Spacer(modifier = Modifier.height(16.dp))
                SliderInput(
                    "Frequency"
                )
                Spacer(modifier = Modifier.height(16.dp))
                SliderInput(
                    title = "Severity",
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