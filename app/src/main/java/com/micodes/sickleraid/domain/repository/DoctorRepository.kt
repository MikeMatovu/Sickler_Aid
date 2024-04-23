package com.micodes.sickleraid.domain.repository

import androidx.lifecycle.LiveData
import com.micodes.sickleraid.domain.model.Doctor

interface DoctorRepository {
    suspend fun upsertDoctor(doctor: Doctor)
    fun getDoctor(doctorId: Int): LiveData<Doctor>
    fun getAllDoctors(): LiveData<List<Doctor>>
    suspend fun deleteDoctor(doctor: Doctor)

}