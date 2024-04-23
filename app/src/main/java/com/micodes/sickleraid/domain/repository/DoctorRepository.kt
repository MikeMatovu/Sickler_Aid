package com.micodes.sickleraid.domain.repository

import androidx.lifecycle.LiveData
import com.micodes.sickleraid.domain.model.Doctor
import com.micodes.sickleraid.domain.model.UserDoctorCrossRef

interface DoctorRepository {
    suspend fun upsertDoctor(doctor: Doctor): Long

    suspend fun upsertUserAndDoctorCrossRef(userDoctorCrossRef: UserDoctorCrossRef)
    suspend fun getDoctor(doctorId: Int): Doctor
    fun getAllDoctors(): LiveData<List<Doctor>>
    suspend fun deleteDoctor(doctor: Doctor)

    suspend fun getDoctorsForUser(userId: String): List<Doctor>

}