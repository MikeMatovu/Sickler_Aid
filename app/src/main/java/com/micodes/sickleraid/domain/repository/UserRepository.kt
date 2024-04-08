package com.micodes.sickleraid.domain.repository

import com.micodes.sickleraid.domain.model.DatabaseOperationResponse
import com.micodes.sickleraid.domain.model.UserDetails

interface UserRepository {
    suspend fun getUser(uid: String): UserDetails?
    suspend fun saveUserDetailsToDatabase(userUpdate: UserDetails): DatabaseOperationResponse
    // Add other user-related methods as needed
}