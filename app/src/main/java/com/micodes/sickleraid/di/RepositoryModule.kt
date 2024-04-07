package com.micodes.sickleraid.di

import com.micodes.sickleraid.data.repository.SicklerAidRepositoryImpl
import com.micodes.sickleraid.domain.repository.SicklerAidRepository
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

}