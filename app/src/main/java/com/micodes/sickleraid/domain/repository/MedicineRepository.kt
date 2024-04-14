package com.micodes.sickleraid.domain.repository

interface MedicineRepository {
    suspend fun insertMedicine()
}