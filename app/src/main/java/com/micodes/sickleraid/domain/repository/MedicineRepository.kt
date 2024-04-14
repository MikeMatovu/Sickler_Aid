package com.micodes.sickleraid.domain.repository

import com.micodes.sickleraid.domain.model.DatabaseOperationResponse
import com.micodes.sickleraid.domain.model.MedicineTable

interface MedicineRepository {
    suspend fun insertMedicine(medicineTable: MedicineTable): DatabaseOperationResponse

    suspend fun getMedicines(userId: String): List<MedicineTable>
}