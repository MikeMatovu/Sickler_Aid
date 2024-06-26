package com.micodes.sickleraid.di

import android.app.Application
import androidx.room.Room
import com.micodes.sickleraid.data.datasource.database.DailyCheckupDao
import com.micodes.sickleraid.data.datasource.database.DoctorDao
import com.micodes.sickleraid.data.datasource.database.MedicalRecordsDao
import com.micodes.sickleraid.data.datasource.database.MedicineDao
import com.micodes.sickleraid.data.datasource.database.SicklerAidDao
import com.micodes.sickleraid.data.datasource.database.SicklerAidDatabase
import com.micodes.sickleraid.data.datasource.database.UserDao
import com.micodes.sickleraid.data.datasource.database.UserDoctorCrossRefDao
import com.micodes.sickleraid.domain.model.UserDoctorCrossRef
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideSicklerAidDatabase(
        application: Application
    ): SicklerAidDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = SicklerAidDatabase::class.java,
            name = "sickler_aid_db"
        ).fallbackToDestructiveMigration().build()

    }

    @Provides
    @Singleton
    fun provideSicklerAidDao(
        sicklerAidDatabase: SicklerAidDatabase
    ): SicklerAidDao = sicklerAidDatabase.sicklerAidDao

    @Provides
    @Singleton
    fun provideUserDao(
        sicklerAidDatabase: SicklerAidDatabase
    ): UserDao = sicklerAidDatabase.userDao

    @Provides
    @Singleton
    fun provideMedicalRecords(
        sicklerAidDatabase: SicklerAidDatabase
    ): MedicalRecordsDao = sicklerAidDatabase.medicalRecordsDao

    @Provides
    @Singleton
    fun provideDailyCheckup(
        sicklerAidDatabase: SicklerAidDatabase
    ): DailyCheckupDao = sicklerAidDatabase.dailyCheckupDao

    @Provides
    @Singleton
    fun provideMedicine(
        sicklerAidDatabase: SicklerAidDatabase
    ): MedicineDao = sicklerAidDatabase.medicineDao

    @Provides
    @Singleton
    fun provideDoctor(
        sicklerAidDatabase: SicklerAidDatabase
    ): DoctorDao = sicklerAidDatabase.doctorDao
    @Provides
    @Singleton
    fun provideUserDoctorCrossRef(
        sicklerAidDatabase: SicklerAidDatabase
    ): UserDoctorCrossRefDao = sicklerAidDatabase.userDoctorCrossRefDao
}