package com.micodes.sickleraid.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.micodes.sickleraid.data.datasource.database.UserDao
import com.micodes.sickleraid.domain.model.DatabaseOperationResponse
import com.micodes.sickleraid.domain.model.Response
import com.micodes.sickleraid.domain.model.UserDetails
import com.micodes.sickleraid.domain.repository.UserRepository
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : UserRepository {


    override suspend fun getUser(uid: String): UserDetails? {
        return userDao.getUser(uid)
    }

//    override suspend fun saveUserDetailsToDatabase(userUpdate: UserDetails): DatabaseOperationResponse {
//        try {
//            val currentUser: FirebaseUser? = auth.currentUser
//            currentUser?.let { firebaseUser ->
//                val user = UserDetails(
//                    uid = firebaseUser.uid,
//                    firstName = firebaseUser.displayName?.split(" ")?.firstOrNull(),
//                    lastName = firebaseUser.displayName?.split(" ")?.lastOrNull(),
//                    email = firebaseUser.email ?: "",
//                    phoneNumber = userUpdate.phoneNumber
//                )
//
//                userDao.insertOrUpdateUser(user)
//
//            }
//            return Response.Success(true)
//        } catch (e: FirebaseAuthException) {
//            Log.e("Operation", "Failed to save user details: ${e.message}")
//            return Response.Failure(e)
//        } catch (e: Exception) {
//            Log.e("Operation", "Failed to save user details: ${e.message}")
//            return Response.Failure(e)
//        }
//    }


    override suspend fun saveUserDetailsToDatabase(userUpdate: UserDetails): DatabaseOperationResponse {
        try {
            val currentUser: FirebaseUser? = auth.currentUser
            currentUser?.let { firebaseUser ->
                val user = UserDetails(
                    uid = firebaseUser.uid,
                    firstName = firebaseUser.displayName?.split(" ")?.firstOrNull(),
                    lastName = firebaseUser.displayName?.split(" ")?.lastOrNull(),
                    email = firebaseUser.email ?: "",
                    phoneNumber = userUpdate.phoneNumber
                )

                // Save user details to Firestore instead of Room
                firestore.collection("users").document(user.uid).set(user)
            }
            return Response.Success(true)
        } catch (e: FirebaseAuthException) {
            Log.e("Operation", "Failed to save user details: ${e.message}")
            return Response.Failure(e)
        } catch (e: Exception) {
            Log.e("Operation", "Failed to save user details: ${e.message}")
            return Response.Failure(e)
        }
    }

    // Implement other methods as defined in the UserRepository interface
}