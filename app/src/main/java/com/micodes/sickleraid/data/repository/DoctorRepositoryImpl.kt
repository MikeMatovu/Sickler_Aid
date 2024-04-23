package com.micodes.sickleraid.data.repository

import androidx.lifecycle.LiveData
import com.micodes.sickleraid.data.datasource.database.DoctorDao
import com.micodes.sickleraid.domain.model.Doctor
import com.micodes.sickleraid.domain.repository.DoctorRepository
import javax.inject.Inject

class DoctorRepositoryImpl @Inject constructor(
    private val dao: DoctorDao
) : DoctorRepository {
    override suspend fun upsertDoctor(doctor: Doctor) {
        TODO("Not yet implemented")
    }

    override fun getDoctor(doctorId: Int): LiveData<Doctor> {
        TODO("Not yet implemented")
    }

    override fun getAllDoctors(): LiveData<List<Doctor>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDoctor(doctor: Doctor) {
        TODO("Not yet implemented")
    }
}