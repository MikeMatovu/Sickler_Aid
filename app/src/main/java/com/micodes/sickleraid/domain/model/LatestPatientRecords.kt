package com.micodes.sickleraid.domain.model

data class LatestPatientRecords(
    val latestDailyCheckup: DailyCheckup,
    val latestMedicalRecord: MedicalRecordsType
)


