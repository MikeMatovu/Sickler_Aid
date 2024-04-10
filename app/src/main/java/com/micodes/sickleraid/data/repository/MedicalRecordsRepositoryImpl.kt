package com.micodes.sickleraid.data.repository

import com.micodes.sickleraid.data.datasource.database.MedicalRecordsDao
import com.micodes.sickleraid.domain.model.DatabaseOperationResponse
import com.micodes.sickleraid.domain.model.MedicalRecords
import com.micodes.sickleraid.domain.model.Response
import com.micodes.sickleraid.domain.repository.MedicalRecordsRepository
import javax.inject.Inject

open class MedicalRecordsRepositoryImpl @Inject constructor(
    private val dao: MedicalRecordsDao
) : MedicalRecordsRepository {

    override suspend fun insertMedicalRecords(records: MedicalRecords):DatabaseOperationResponse {
        return try {
            dao.insertRecords(records)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getLatestRecord(userId: String) :MedicalRecords? {
        return dao.getLatestMedicalRecord(userId)
    }
}