package com.micodes.sickleraid.di

import com.micodes.sickleraid.data.remote.AccountServiceImpl
import com.micodes.sickleraid.domain.services.AccountService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService


}