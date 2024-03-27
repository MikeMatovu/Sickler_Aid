package com.micodes.sickleraid.data.datasource.api.callUtil

import androidx.room.Transaction
import retrofit2.Response
import retrofit2.http.GET


interface ApiCalls{

    @GET("/getAll")
    suspend fun getAllTransactions(): Response<List<Transaction>>

    @GET("/size")
    suspend fun  getSize():Response<String>

}