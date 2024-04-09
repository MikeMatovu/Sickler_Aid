package com.micodes.sickleraid.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.micodes.sickleraid.domain.model.MedicalRecords

@Dao
interface MedicalRecordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecords(records: MedicalRecords)

    @Query("SELECT * FROM medical_records WHERE userId = :userId ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestMedicalRecord(userId: String): MedicalRecords?

}