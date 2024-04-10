package com.micodes.sickleraid.presentation.symptoms

import androidx.lifecycle.ViewModel
import com.micodes.sickleraid.domain.services.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SymptomsViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {
    private val _state = MutableStateFlow(SymptomsUiState())
    val state = _state.asStateFlow()

    fun onChipSelected(chipIndex: Int) {
        _state.value = _state.value.copy(selectedChip = chipIndex)
    }

    fun onFrequencySliderValueChanged(value: Float, index: Int) {
        val chipItems = _state.value.chipItems.toMutableList()
        chipItems[index] = chipItems[index].copy(frequencySliderValue = value)
        _state.value = _state.value.copy(chipItems = chipItems)
    }

    fun onSeveritySliderValueChanged(value: Float, index: Int) {
        val chipItems = _state.value.chipItems.toMutableList()
        chipItems[index] = chipItems[index].copy(severitySliderValue = value)
        _state.value = _state.value.copy(chipItems = chipItems)
    }

}