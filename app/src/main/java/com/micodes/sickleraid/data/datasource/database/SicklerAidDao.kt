package com.micodes.sickleraid.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.micodes.sickleraid.domain.model.Doctor
import java.util.Date

@Dao
interface SicklerAidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDoctor(doctor: Doctor)


}

fun getCurrentDateTime(): Date {
    return Date()
}
