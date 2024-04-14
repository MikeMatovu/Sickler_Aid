package com.micodes.sickleraid.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.micodes.sickleraid.data.datasource.database.MedicineDao
import com.micodes.sickleraid.domain.model.DatabaseOperationResponse
import com.micodes.sickleraid.domain.model.MedicineTable
import com.micodes.sickleraid.domain.model.Response
import com.micodes.sickleraid.domain.repository.MedicineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MedicineRepositoryImpl @Inject constructor(
    private val dao: MedicineDao,
    private val auth: FirebaseAuth,
): MedicineRepository {
    override suspend fun insertMedicine(medicineTable: MedicineTable):DatabaseOperationResponse {
        return try {
             dao.insertMedicine(medicineTable)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getMedicines(userId: String): List<MedicineTable> {
        return withContext(Dispatchers.IO) {
            // Perform database operations here
            dao.getMedicines(userId)
        }
    }
}