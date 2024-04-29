package com.micodes.sickleraid.domain.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.micodes.sickleraid.domain.model.DatabaseOperationResponse
import com.micodes.sickleraid.domain.model.DoctorRecommendation
import com.micodes.sickleraid.domain.model.FirebaseDoctor
import com.micodes.sickleraid.domain.model.FirebaseMedicalRecord

interface FirebaseRepository {

    suspend fun createDoctor(doctor: FirebaseDoctor): FirebaseDoctor
    suspend fun updateDoctor(doctor: FirebaseDoctor)
    suspend fun deleteDoctor(doctor: FirebaseDoctor)
    suspend fun getDoctor(id: String): FirebaseDoctor?
    suspend fun getAllDoctors(): List<FirebaseDoctor>


    suspend fun createMedicalRecord(medicalRecord: FirebaseMedicalRecord): DatabaseOperationResponse
    suspend fun getMedicalRecord(id: String): FirebaseMedicalRecord?
    suspend fun getLatestRecord(userId: String): FirebaseMedicalRecord?

    suspend fun getLatestRecommendation(userId: String): DoctorRecommendation?
}