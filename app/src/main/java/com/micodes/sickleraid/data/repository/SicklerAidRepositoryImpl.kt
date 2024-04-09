package com.micodes.sickleraid.data.repository

import androidx.lifecycle.LiveData
import com.micodes.sickleraid.data.datasource.database.SicklerAidDao
import com.micodes.sickleraid.domain.model.Doctor
import com.micodes.sickleraid.domain.model.MedicalRecords
import com.micodes.sickleraid.domain.repository.SicklerAidRepository
import javax.inject.Inject

open class SicklerAidRepositoryImpl @Inject constructor(
    val dao: SicklerAidDao
) : SicklerAidRepository{
    override suspend fun getAllDoctors(): LiveData<List<Doctor>> {
        return dao.getAllDoctors()
    }

    override suspend fun upsertDoctor(doctor: Doctor) {
        dao.upsert(doctor)
    }

    override suspend fun insertMedicalRecords(records: MedicalRecords) {
        TODO("Not yet implemented")
    }
}