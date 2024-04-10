package com.micodes.sickleraid.domain.repository

import com.micodes.sickleraid.domain.model.DailyCheckup

interface DailyCheckupRepository {

    suspend fun insertDailyCheckup(records: DailyCheckup)

    suspend fun getLatestCheckup(userId: String): DailyCheckup?
}