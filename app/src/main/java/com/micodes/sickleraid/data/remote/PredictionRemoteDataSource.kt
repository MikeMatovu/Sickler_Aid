package com.micodes.sickleraid.data.remote

import com.micodes.sickleraid.data.remote.dto.PredictionResponse

interface PredictionRemoteDataSource {
    suspend fun getPrediction(
//        sn: Int,
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
}