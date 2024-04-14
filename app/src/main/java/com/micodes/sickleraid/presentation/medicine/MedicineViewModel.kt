package com.micodes.sickleraid.presentation.medicine

import android.util.Log
import androidx.lifecycle.ViewModel
import com.micodes.sickleraid.domain.repository.MedicineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val medicineRepository: MedicineRepository
) : ViewModel() {

    fun test() {
        Log.d("MedicineViewModel", "Test")
    }

//        fun getMedicine() = medicineRepository.getMedicine()
//        fun getMedicineById(id: String) = medicineRepository.getMedicineById(id)
//        fun getMedicineByName(name: String) = medicineRepository.getMedicineByName(name)
//        fun getMedicineByType(type: String) = medicineRepository.getMedicineByType(type)
//        fun getMedicineByCategory(category: String) = medicineRepository.getMedicineByCategory(category)
//        fun getMedicineByPrice(price: Int) = medicineRepository.getMedicineByPrice(price)
//        fun getMedicineByQuantity(quantity: Int) = medicineRepository.getMedicineByQuantity(quantity)
//        fun getMedicineByExpiryDate(expiryDate: String) = medicineRepository.getMedicineByExpiryDate(expiryDate)
//        fun getMedicineByManufactureDate(manufactureDate: String) = medicineRepository.getMedicine
}