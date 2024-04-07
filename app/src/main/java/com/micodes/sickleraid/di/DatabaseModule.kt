package com.micodes.sickleraid.di

import android.app.Application
import androidx.room.Room
import com.micodes.sickleraid.data.datasource.database.SicklerAidDao
import com.micodes.sickleraid.data.datasource.database.SicklerAidDatabase
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
    fun provideNewsDao(
        sicklerAidDatabase: SicklerAidDatabase
    ): SicklerAidDao = sicklerAidDatabase.sicklerAidDao
}