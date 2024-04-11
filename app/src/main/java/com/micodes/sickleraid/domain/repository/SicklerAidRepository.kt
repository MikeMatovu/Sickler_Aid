package com.micodes.sickleraid.domain.repository

import androidx.lifecycle.LiveData
import com.micodes.sickleraid.domain.model.Doctor
import com.micodes.sickleraid.domain.model.LatestPatientRecords

interface SicklerAidRepository {
    //Functions
    suspend fun getAllDoctors(): LiveData<List<Doctor>>

    suspend fun upsertDoctor(doctor: Doctor)


    suspend fun getLatestPatientRecords(userId: String): LatestPatientRecords?

    suspend fun fetchHtmlContent(url: String): String
    suspend fun scrapeEducationalMaterials(html: String): List<Pair<String, String>>

}