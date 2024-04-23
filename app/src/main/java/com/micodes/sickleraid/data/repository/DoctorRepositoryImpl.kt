package com.micodes.sickleraid.data.repository

import androidx.lifecycle.LiveData
import com.micodes.sickleraid.data.datasource.database.DoctorDao
import com.micodes.sickleraid.data.datasource.database.UserDoctorCrossRefDao
import com.micodes.sickleraid.domain.model.Doctor
import com.micodes.sickleraid.domain.model.UserDoctorCrossRef
import com.micodes.sickleraid.domain.repository.DoctorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DoctorRepositoryImpl @Inject constructor(
    private val dao: DoctorDao,
    private val userDoctorCrossRefDao: UserDoctorCrossRefDao
) : DoctorRepository {
    override suspend fun upsertDoctor(doctor: Doctor): Long {
        return dao.upsertDoctor(doctor)
    }

    override suspend fun upsertUserAndDoctorCrossRef(userDoctorCrossRef: UserDoctorCrossRef) {
        userDoctorCrossRefDao.insertUserDoctorCrossRef(userDoctorCrossRef)
    }

    override suspend fun getDoctor(doctorId: Int): Doctor {
        return dao.getDoctor(doctorId)
    }

    override fun getAllDoctors(): LiveData<List<Doctor>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDoctor(doctor: Doctor) {
        TODO("Not yet implemented")
    }

    override suspend fun getDoctorsForUser(userId: String): List<Doctor> {
        return withContext(Dispatchers.IO) {
            userDoctorCrossRefDao.getDoctorsForUser(userId)
        }
    }
}