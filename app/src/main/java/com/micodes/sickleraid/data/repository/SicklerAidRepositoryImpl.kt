package com.micodes.sickleraid.data.repository

import androidx.lifecycle.LiveData
import com.micodes.sickleraid.data.datasource.database.DailyCheckupDao
import com.micodes.sickleraid.data.datasource.database.MedicalRecordsDao
import com.micodes.sickleraid.data.datasource.database.SicklerAidDao
import com.micodes.sickleraid.domain.model.Doctor
import com.micodes.sickleraid.domain.model.LatestPatientRecords
import com.micodes.sickleraid.domain.repository.SicklerAidRepository
import javax.inject.Inject

open class SicklerAidRepositoryImpl @Inject constructor(
    private val dao: SicklerAidDao,
    private val dailyCheckupDao: DailyCheckupDao,
    private val medicalRecordsDao: MedicalRecordsDao
) : SicklerAidRepository {
    override suspend fun getAllDoctors(): LiveData<List<Doctor>> {
        return dao.getAllDoctors()
    }

    override suspend fun upsertDoctor(doctor: Doctor) {
        dao.upsert(doctor)
    }

    override suspend fun getLatestPatientRecords(userId: String): LatestPatientRecords? {
        val latestDailyCheckup = dailyCheckupDao.getLatestCheckup(userId)
        val latestMedicalRecord = medicalRecordsDao.getLatestMedicalRecord(userId)

        return if (latestDailyCheckup != null && latestMedicalRecord != null) {
            LatestPatientRecords(latestDailyCheckup, latestMedicalRecord)
        } else {
            null
        }
    }

}