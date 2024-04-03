package com.micodes.sickleraid.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.micodes.sickleraid.domain.model.PatientTable

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [PatientTable::class], version = 1, exportSchema = false)
abstract class SicklerAidDatabase : RoomDatabase() {

    abstract fun sicklerAidDao(): SicklerAidDao

    companion object {
        @Volatile
        private var Instance: SicklerAidDatabase? = null

        fun getDatabase(context: Context): SicklerAidDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, SicklerAidDatabase::class.java, "item_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}