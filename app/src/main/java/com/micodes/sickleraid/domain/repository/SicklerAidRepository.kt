package com.micodes.sickleraid.domain.repository

import androidx.lifecycle.LiveData
import com.micodes.sickleraid.data.remote.dto.PredictionResponse
import com.micodes.sickleraid.domain.model.Doctor
import com.micodes.sickleraid.domain.model.LatestPatientRecords

interface SicklerAidRepository {
    //Functions
    suspend fun getAllDoctors(): LiveData<List<Doctor>>

    suspend fun upsertDoctor(doctor: Doctor)


    suspend fun getPrediction(
        sn: Int,
        gender: Int,
        patientAge: Int,
        diagnosisAge: Int,
        bmi: Int,
        pcv: Int,
        crisisFrequency: Int,
        transfusionFrequency: Int,
        spo2: Int,
        systolicBP: Int,
        diastolicBP: Int,
        heartRate: Int,
        respiratoryRate: Int,
        hbF: Int,
        temp: Int,
        mcv: Int,
        platelets: Int,
        alt: Int,
        bilirubin: Int,
        ldh: Int,
        percentageAverage: Int
    ): PredictionResponse

    suspend fun getLatestPatientRecords(userId: String): LatestPatientRecords?

    suspend fun fetchHtmlContent(url: String): String
    suspend fun scrapeEducationalMaterials(html: String): List<Pair<String, String>>

}