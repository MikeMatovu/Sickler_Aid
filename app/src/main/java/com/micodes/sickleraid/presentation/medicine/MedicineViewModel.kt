package com.micodes.sickleraid.presentation.medicine

import androidx.lifecycle.ViewModel
import com.micodes.sickleraid.domain.repository.MedicineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val medicineRepository: MedicineRepository
) : ViewModel() {

}