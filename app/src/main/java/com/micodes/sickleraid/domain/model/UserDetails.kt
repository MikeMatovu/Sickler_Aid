package com.micodes.sickleraid.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDetails(
    @PrimaryKey
    val uid: String = "",
    val isAnonymous: Boolean = true,
    val email: String = "",
    val phoneNumber: String? = "",
    val firstName: String?,
    val lastName: String?,
    val photoUrl: String = "",
    val emailVerified: Boolean = false,
)
