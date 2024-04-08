package com.micodes.sickleraid.di

import com.micodes.sickleraid.data.repository.SicklerAidRepositoryImpl
import com.micodes.sickleraid.data.repository.UserRepositoryImpl
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

}