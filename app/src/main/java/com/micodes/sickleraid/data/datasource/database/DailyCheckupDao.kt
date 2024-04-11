package com.micodes.sickleraid.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.micodes.sickleraid.domain.model.DailyCheckup
import com.micodes.sickleraid.domain.model.TemperatureRecord

@Dao
interface DailyCheckupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecords(records: DailyCheckup)

    @Query("SELECT * FROM daily_checkup WHERE userId = :userId ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestCheckup(userId: String): DailyCheckup?

    @Query("SELECT temperature, timestamp FROM daily_checkup WHERE userId = :userId")
    suspend fun getTemperatureRecords(userId: String): List<TemperatureRecord>

}