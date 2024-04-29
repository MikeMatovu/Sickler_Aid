package com.micodes.sickleraid.data.remote

import com.micodes.sickleraid.data.remote.dto.PredictionRequest
import com.micodes.sickleraid.data.remote.dto.PredictionResponse
import javax.inject.Inject

class RetrofitPredictionRemoteDataSource @Inject constructor(
    private val predictionApi: PredictionApi
) : PredictionRemoteDataSource{
    override  suspend fun getPrediction(
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
    ): PredictionResponse {
        val request = PredictionRequest(
//            sn,
            gender,
            patientAge,
            diagnosisAge,
            bmi,
            pcv,
            crisisFrequency,
            transfusionFrequency,
            spo2,
            systolicBP,
            diastolicBP,
            heartRate,
            respiratoryRate,
            hbF,
            temp,
            mcv,
            platelets,
            alt,
            bilirubin,
            ldh,
            percentageAverage
        )
        return predictionApi.getPrediction(request)
    }

}
