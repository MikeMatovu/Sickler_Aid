package com.micodes.sickleraid.data.remote.dto

data class PredictionRequest(
//    val sn: Int,
    val gender: Int,
    val patientAge: Int,
    val diagnosisAge: Int,
    val bmi: Int,
    val pcv: Int,
    val crisisFrequency: Int,
    val transfusionFrequency: Int,
    val spo2: Int,
    val systolicBP: Int,
    val diastolicBP: Int,
    val heartRate: Int,
    val respiratoryRate: Int,
    val hbF: Int,
    val temp: Int,
    val mcv: Int,
    val platelets: Int,
    val alt: Int,
    val bilirubin: Int,
    val ldh: Int,
    val percentageAverage: Int
)
