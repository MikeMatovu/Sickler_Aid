package com.micodes.sickleraid.domain.repository

import com.micodes.sickleraid.domain.model.DailyCheckup
import com.micodes.sickleraid.domain.model.DatabaseOperationResponse

interface DailyCheckupRepository {

    suspend fun insertDailyCheckup(records: DailyCheckup): DatabaseOperationResponse

    suspend fun getLatestCheckup(userId: String): DailyCheckup?
}