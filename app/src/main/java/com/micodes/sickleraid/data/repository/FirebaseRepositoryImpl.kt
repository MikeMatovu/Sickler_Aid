package com.micodes.sickleraid.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.micodes.sickleraid.domain.model.DatabaseOperationResponse
import com.micodes.sickleraid.domain.model.DoctorRecommendation
import com.micodes.sickleraid.domain.model.FirebaseDoctor
import com.micodes.sickleraid.domain.model.FirebaseMedicalRecord
import com.micodes.sickleraid.domain.model.Response
import com.micodes.sickleraid.domain.repository.FirebaseRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : FirebaseRepository {

    private val doctorCollection = fireStore.collection("doctors")
    private val medicalRecordsCollection = fireStore.collection("medical_records")
    private val recommendationCollection = fireStore.collection("doctor_recommendations")

    override suspend fun createDoctor(doctor: FirebaseDoctor): FirebaseDoctor {
        val docRef = doctorCollection.document()
        val newDoctor = doctor.copy(id = docRef.id)
        docRef.set(newDoctor).await()
        return newDoctor
    }

    override suspend fun updateDoctor(doctor: FirebaseDoctor) {
        doctorCollection.document(doctor.id).set(doctor).await()
    }

    override suspend fun deleteDoctor(doctor: FirebaseDoctor) {
        doctorCollection.document(doctor.id).delete().await()
    }

    override suspend fun getDoctor(id: String): FirebaseDoctor? {
        val doc = doctorCollection.document(id).get().await()
        return doc.toObject(FirebaseDoctor::class.java)
    }

    override suspend fun getAllDoctors(): List<FirebaseDoctor> {
        val querySnapshot = doctorCollection.get().await()
        return querySnapshot.documents.mapNotNull { it.toObject(FirebaseDoctor::class.java) }
    }

    //Medical records

    override suspend fun createMedicalRecord(medicalRecord: FirebaseMedicalRecord): DatabaseOperationResponse {
        return try {
            val docRef = medicalRecordsCollection.document()
            val newMedicalRecord = medicalRecord.copy(id = docRef.id)
            docRef.set(newMedicalRecord).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getMedicalRecord(id: String): FirebaseMedicalRecord? {
        val doc = medicalRecordsCollection.document(id).get().await()
        return doc.toObject(FirebaseMedicalRecord::class.java)
    }

    override suspend fun getLatestRecord(userId: String): FirebaseMedicalRecord? {
        val querySnapshot = medicalRecordsCollection
            .whereEqualTo("userId", userId)
            .orderBy("timestamp")
            .limitToLast(1)
            .get()
            .await()
        return querySnapshot.documents.firstOrNull()?.toObject(FirebaseMedicalRecord::class.java)
    }

    override suspend fun getLatestRecommendation(userId: String): DoctorRecommendation? {
        try {
            val querySnapshot = recommendationCollection
                .whereEqualTo("userId", userId)
                .orderBy("timestamp")
                .limitToLast(1)
                .get()
                .await()
            val recommendation =
                querySnapshot.documents.firstOrNull()?.toObject(DoctorRecommendation::class.java)
            if (recommendation == null) {
                Log.d("getLatestRecommendation", "No recommendation found for user: $userId")
            } else {
                Log.d("getLatestRecommendation", "Fetched recommendation: $recommendation")
            }
            return recommendation
        } catch (e: Exception) {
            Log.e("getLatestRecommendation", "Error fetching recommendation for user: $userId", e)
            return null
        }
    }
}