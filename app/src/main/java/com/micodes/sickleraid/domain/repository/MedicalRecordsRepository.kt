package com.micodes.sickleraid.domain.repository

import com.micodes.sickleraid.domain.model.MedicalRecords


interface MedicalRecordsRepository {

    suspend fun insertMedicalRecords(records: MedicalRecords)

    suspend fun getLatestRecord(userId: String): MedicalRecords?

}