package com.micodes.sickleraid.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "medical_records",
    foreignKeys = [
        ForeignKey(
            entity = UserDetails::class,
            childColumns = ["userId"],
            parentColumns = ["uid"]
        )
    ]
)
data class MedicalRecords(
    @PrimaryKey(autoGenerate = true)
    val recordId: Int = 0,
    val userId: String = "",
    val bmi: Int = 0,
    val weight: Int = 0,
    val packetCellVolume: Int = 0,
    val platelets: Int = 0,
    val birulubin: Int = 0,
    val ldh: Int = 0,
    val fetalHaemoglobin: Int = 0,
    val meanCorpuscularVolume: Int = 0,
    val aat: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

data class FirebaseMedicalRecord(
    val id: String = "",
    val userId: String = "",
    val bmi: Int = 0,
    val weight: Int = 0,
    val packetCellVolume: Int = 0,
    val platelets: Int = 0,
    val birulubin: Int = 0,
    val ldh: Int = 0,
    val fetalHaemoglobin: Int = 0,
    val meanCorpuscularVolume: Int = 0,
    val aat: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)