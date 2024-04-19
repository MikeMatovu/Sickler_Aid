package com.micodes.sickleraid.data.remote

import com.micodes.sickleraid.data.remote.dto.PredictionRequest
import com.micodes.sickleraid.data.remote.dto.PredictionResponse
import retrofit2.http.Body
import retrofit2.http.POST

    interface PredictionApi {
        @POST("everything")
        suspend fun getPrediction(@Body request: PredictionRequest): PredictionResponse
    }
