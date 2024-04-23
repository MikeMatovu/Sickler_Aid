package com.micodes.sickleraid.data.datasource.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.micodes.sickleraid.domain.model.Doctor
import com.micodes.sickleraid.domain.model.UserDetails
import com.micodes.sickleraid.domain.model.UserDoctorCrossRef

/**
 * Data class to hold the relationship between UserDetails and Doctors
 */
data class UserDetailsWithDoctors(
    @Embedded val userDetails: UserDetails,
    @Relation(
        parentColumn = "uid",
        entityColumn = "id",
        associateBy = Junction(UserDoctorCrossRef::class)
    )
    val doctors: List<Doctor>
)

data class DoctorWithUsers(
    @Embedded val doctor: Doctor,
    @Relation(
        parentColumn = "id",
        entityColumn = "uid",
        associateBy = Junction(UserDoctorCrossRef::class)
    )
    val users: List<UserDetails>
)
