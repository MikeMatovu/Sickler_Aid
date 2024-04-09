package com.micodes.sickleraid.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.micodes.sickleraid.domain.model.Doctor
import com.micodes.sickleraid.domain.model.MedicalRecords
import com.micodes.sickleraid.domain.model.PatientTable
import com.micodes.sickleraid.domain.model.UserDetails

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [PatientTable::class, Doctor::class, UserDetails::class, MedicalRecords::class], version = 6, exportSchema = false)
abstract class SicklerAidDatabase : RoomDatabase() {

    abstract val sicklerAidDao: SicklerAidDao
    abstract val userDao: UserDao
    abstract val medicalRecordsDao: MedicalRecordsDao

}