package com.micodes.sickleraid.data.repository

import com.micodes.sickleraid.data.datasource.database.DailyCheckupDao
import com.micodes.sickleraid.domain.model.DailyCheckup
import com.micodes.sickleraid.domain.repository.DailyCheckupRepository

import javax.inject.Inject

open  class DailyCheckupRepositoryImpl @Inject constructor(
    private val dailyCheckupDao: DailyCheckupDao
) : DailyCheckupRepository {
    override suspend fun insertDailyCheckup(records: DailyCheckup) {
        dailyCheckupDao.insertRecords(records)
    }

    override suspend fun getLatestCheckup(userId: String): DailyCheckup? {
        return dailyCheckupDao.getLatestCheckup(userId)
    }
}