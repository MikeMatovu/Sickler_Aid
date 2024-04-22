package com.micodes.sickleraid.di

import com.micodes.sickleraid.data.remote.PredictionRemoteDataSource
import com.micodes.sickleraid.data.remote.RetrofitPredictionRemoteDataSource
import com.micodes.sickleraid.data.repository.DailyCheckupRepositoryImpl
import com.micodes.sickleraid.data.repository.DoctorRepositoryImpl
import com.micodes.sickleraid.data.repository.MedicalRecordsRepositoryImpl
import com.micodes.sickleraid.data.repository.MedicineRepositoryImpl
import com.micodes.sickleraid.data.repository.SicklerAidRepositoryImpl
import com.micodes.sickleraid.data.repository.UserRepositoryImpl
import com.micodes.sickleraid.domain.repository.DailyCheckupRepository
import com.micodes.sickleraid.domain.repository.DoctorRepository
import com.micodes.sickleraid.domain.repository.MedicalRecordsRepository
import com.micodes.sickleraid.domain.repository.MedicineRepository
import com.micodes.sickleraid.domain.repository.SicklerAidRepository
import com.micodes.sickleraid.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSicklerAidRepository(sicklerRepositoryImpl: SicklerAidRepositoryImpl): SicklerAidRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindMedicalRecordsRepository(medicalRecordsRepositoryImpl: MedicalRecordsRepositoryImpl): MedicalRecordsRepository


    @Binds
    @Singleton
    abstract fun bindDailyCheckupRepository(dailyCheckupRepositoryImpl: DailyCheckupRepositoryImpl): DailyCheckupRepository

    @Binds
    @Singleton
    abstract fun bindMedicineRepository(medicineRepositoryImpl: MedicineRepositoryImpl): MedicineRepository


    @Binds
    @Singleton
    abstract fun bindDoctorRepository(doctorRepositoryImpl: DoctorRepositoryImpl): DoctorRepository


    @Binds
    @Singleton
    abstract fun bindPredictionRemoteDataSource(dataSource: RetrofitPredictionRemoteDataSource): PredictionRemoteDataSource
}
