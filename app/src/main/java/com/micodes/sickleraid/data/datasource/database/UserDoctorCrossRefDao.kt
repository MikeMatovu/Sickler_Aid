package com.micodes.sickleraid.data.datasource.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.micodes.sickleraid.domain.model.Doctor
import com.micodes.sickleraid.domain.model.UserDoctorCrossRef

@Dao
interface DoctorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDoctor(doctor: Doctor): Long

    @Query("SELECT * FROM Doctor WHERE id = :doctorId")
    suspend fun getDoctor(doctorId: Int): Doctor

    @Query("SELECT * FROM Doctor")
    fun getAllDoctors(): LiveData<List<Doctor>>

    @Delete
    suspend fun deleteDoctor(doctor: Doctor)

}

@Dao
interface UserDoctorCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserDoctorCrossRef(userDoctorCrossRef: UserDoctorCrossRef)

    @Transaction
    @Query("SELECT Doctor.*, UserDoctorCrossRef.userId FROM Doctor INNER JOIN UserDoctorCrossRef ON Doctor.id = UserDoctorCrossRef.doctorId WHERE UserDoctorCrossRef.userId = :userId")
    fun getDoctorsForUser(userId: String): List<Doctor>
//
//    @Query("SELECT * FROM UserDoctorCrossRef WHERE doctorId = :doctorId")
//    fun getUsersForDoctor(doctorId: Int): LiveData<List<UserDetails>>
//
//    @Transaction
//    @Query("SELECT * FROM Doctor WHERE id IN (SELECT doctorId FROM UserDoctorCrossRef WHERE userId = :userId)")
//    fun getDoctorsForUserWithDetails(userId: String): LiveData<List<DoctorWithUsers>>
//
//    @Transaction
//    @Query("SELECT * FROM users WHERE uid IN (SELECT userId FROM UserDoctorCrossRef WHERE doctorId = :doctorId)")
//    fun getUsersForDoctorWithDetails(doctorId: Int): LiveData<List<UserDetailsWithDoctors>>
}

data class DoctorWithUserId(
    @Embedded val doctor: Doctor,
    val userId: String
)