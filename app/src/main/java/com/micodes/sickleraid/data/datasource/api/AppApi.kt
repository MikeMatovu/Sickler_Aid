package com.micodes.sickleraid.data.datasource.api

import com.micodes.sickleraid.domain.model.PatientTable
import retrofit2.Response
import retrofit2.http.*

interface AppApi {

    //TODO: Add the endpoint for the API
    @GET("/api/v1/example")
    suspend fun getExampleResult(): Response<List<PatientTable>>

}