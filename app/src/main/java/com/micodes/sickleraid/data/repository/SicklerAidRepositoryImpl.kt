package com.micodes.sickleraid.data.repository

import androidx.lifecycle.LiveData
import com.micodes.sickleraid.data.datasource.database.DailyCheckupDao
import com.micodes.sickleraid.data.datasource.database.MedicalRecordsDao
import com.micodes.sickleraid.data.datasource.database.SicklerAidDao
import com.micodes.sickleraid.data.remote.PredictionRemoteDataSource
import com.micodes.sickleraid.data.remote.dto.PredictionResponse
import com.micodes.sickleraid.domain.model.Doctor
import com.micodes.sickleraid.domain.model.LatestPatientRecords
import com.micodes.sickleraid.domain.repository.SicklerAidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import javax.inject.Inject

open class SicklerAidRepositoryImpl @Inject constructor(
    private val dao: SicklerAidDao,
    private val dailyCheckupDao: DailyCheckupDao,
    private val medicalRecordsDao: MedicalRecordsDao,
    private val predictionRemoteDataSource: PredictionRemoteDataSource
) : SicklerAidRepository {
    override suspend fun getPrediction(
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
    ): PredictionResponse {
        return predictionRemoteDataSource.getPrediction(
            sn, gender, patientAge, diagnosisAge, bmi, pcv, crisisFrequency, transfusionFrequency,
            spo2, systolicBP, diastolicBP, heartRate, respiratoryRate, hbF, temp, mcv, platelets, alt,
            bilirubin, ldh, percentageAverage
        )
    }


    override suspend fun getLatestPatientRecords(userId: String): LatestPatientRecords? {
        val latestDailyCheckup = dailyCheckupDao.getLatestCheckup(userId)
        val latestMedicalRecord = medicalRecordsDao.getLatestMedicalRecord(userId)

        return if (latestDailyCheckup != null && latestMedicalRecord != null) {
            LatestPatientRecords(latestDailyCheckup, latestMedicalRecord)
        } else {
            null
        }
    }

    override suspend fun fetchHtmlContent(url: String): String {
        return try {
            withContext(Dispatchers.IO) {
                Jsoup.connect(url).get().html()
            }
        }catch ( e: Exception){
            ""
        }
    }

    override suspend fun scrapeEducationalMaterials(html: String): List<Pair<String, String>> {
        val doc: Document = Jsoup.parse(html)
        val educationalMaterials = mutableListOf<Pair<String, String>>()

        // Extracting educational materials
        val items = doc.select("h2:contains(Educational Materials) + ul > li > a")
        for (item in items) {
            val link = item.attr("href")
            val title = item.text()
            educationalMaterials.add(Pair(title, link))
        }

        return educationalMaterials
    }

}