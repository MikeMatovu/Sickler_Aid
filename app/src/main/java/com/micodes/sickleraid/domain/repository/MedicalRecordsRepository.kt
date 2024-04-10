package com.micodes.sickleraid.domain.repository

import com.micodes.sickleraid.domain.model.DatabaseOperationResponse
import com.micodes.sickleraid.domain.model.MedicalRecords


interface MedicalRecordsRepository {

    suspend fun insertMedicalRecords(records: MedicalRecords): DatabaseOperationResponse

    suspend fun getLatestRecord(userId: String): MedicalRecords?

}