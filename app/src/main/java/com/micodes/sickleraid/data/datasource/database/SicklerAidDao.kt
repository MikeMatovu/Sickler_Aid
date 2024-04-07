package com.micodes.sickleraid.data.datasource.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.micodes.sickleraid.domain.model.Doctor
import java.util.Date

@Dao
interface SicklerAidDao{



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(doctor: Doctor)

    @Delete
    suspend fun delete(doctor: Doctor)

    @Query("SELECT * FROM Doctor")
    fun getAllDoctors(): LiveData<List<Doctor>>

}

fun getCurrentDateTime(): Date {
    return Date()
}
