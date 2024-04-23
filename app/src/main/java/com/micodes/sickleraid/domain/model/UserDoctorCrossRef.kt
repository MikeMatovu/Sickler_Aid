package com.micodes.sickleraid.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "UserDoctorCrossRef",
    primaryKeys = ["userId", "doctorId"],
    foreignKeys = [
        ForeignKey(
            entity = UserDetails::class,
            parentColumns = ["uid"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Doctor::class,
            parentColumns = ["id"],
            childColumns = ["doctorId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserDoctorCrossRef(
    val userId: String,
    val doctorId: Int
)
