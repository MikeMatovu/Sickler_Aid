package com.micodes.sickleraid.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.micodes.sickleraid.domain.model.DailyCheckup
import com.micodes.sickleraid.domain.model.Doctor
import com.micodes.sickleraid.domain.model.MedicalRecords
import com.micodes.sickleraid.domain.model.MedicineTable
import com.micodes.sickleraid.domain.model.PatientTable
import com.micodes.sickleraid.domain.model.UserDetails
import com.micodes.sickleraid.domain.model.UserDoctorCrossRef

/**
 * Database class with a singleton Instance object.
 */
@Database(
    entities = [
        PatientTable::class,
        Doctor::class,
        UserDetails::class,
        MedicalRecords::class,
        DailyCheckup::class,
        MedicineTable::class,
        UserDoctorCrossRef::class,
    ],
    version = 10,
    exportSchema = false
)
abstract class SicklerAidDatabase : RoomDatabase() {

    abstract val sicklerAidDao: SicklerAidDao
    abstract val userDao: UserDao
    abstract val medicalRecordsDao: MedicalRecordsDao
    abstract val dailyCheckupDao: DailyCheckupDao
    abstract val medicineDao: MedicineDao
    abstract val doctorDao: DoctorDao
    abstract val userDoctorCrossRefDao: UserDoctorCrossRefDao

}