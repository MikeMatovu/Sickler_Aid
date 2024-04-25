package com.micodes.sickleraid.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Doctor")
data class Doctor(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,

)


data class FirebaseDoctor(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = ""
)