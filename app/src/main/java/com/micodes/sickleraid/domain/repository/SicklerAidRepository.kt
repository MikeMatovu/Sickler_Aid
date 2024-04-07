package com.micodes.sickleraid.domain.repository

import androidx.lifecycle.LiveData
import com.micodes.sickleraid.domain.model.Doctor

interface SicklerAidRepository {
//Functions
    suspend fun getAllDoctors(): LiveData<List<Doctor>>

    suspend fun upsertDoctor(doctor: Doctor)

}