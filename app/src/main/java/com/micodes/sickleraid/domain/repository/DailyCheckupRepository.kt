package com.micodes.sickleraid.domain.repository

import com.micodes.sickleraid.domain.model.DailyCheckup
import com.micodes.sickleraid.domain.model.DatabaseOperationResponse
import com.micodes.sickleraid.domain.model.TemperatureRecord

interface DailyCheckupRepository {

    suspend fun insertDailyCheckup(records: DailyCheckup): DatabaseOperationResponse

    suspend fun getLatestCheckup(userId: String): DailyCheckup?

    suspend fun getAllCheckups(userId: String): List<DailyCheckup>

    suspend fun getTemperatureRecords(userId: String): List<TemperatureRecord>
}