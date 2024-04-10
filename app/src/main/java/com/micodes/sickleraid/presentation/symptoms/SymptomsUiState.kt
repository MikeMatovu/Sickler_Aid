package com.micodes.sickleraid.presentation.symptoms

import com.micodes.sickleraid.presentation.symptoms.components.ChipData

data class SymptomsUiState(
    val selectedChip: Int = 0,
    val chipItems: List<ChipData> = listOf(
        ChipData(
            label = "Headache",
            frequencySliderValue = 0f,
            severitySliderValue = 0f
        ),
        ChipData(
            label = "Pain",
            frequencySliderValue = 0f,
            severitySliderValue = 0f
        ),
        ChipData(
            label = "Fatigue",
            frequencySliderValue = 0f,
            severitySliderValue = 0f
        ),
        ChipData(
            label = "Nausea",
            frequencySliderValue = 0f,
            severitySliderValue = 0f
        ),
        ChipData(
            label = "Fever",
            frequencySliderValue = 0f,
            severitySliderValue = 0f
        ),
        ChipData(
            label = "Shortness of Breath",
            frequencySliderValue = 0f,
            severitySliderValue = 0f
        ),
        ChipData(
            label = "Chest pain",
            frequencySliderValue = 0f,
            severitySliderValue = 0f
        ),
        ChipData(
            label = "Anxiety",
            frequencySliderValue = 0f,
            severitySliderValue = 0f
        ),
        ChipData(
            label = "Depression",
            frequencySliderValue = 0f,
            severitySliderValue = 0f
        )

    )
)