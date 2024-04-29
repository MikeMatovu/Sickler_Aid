package com.micodes.sickleraid.data.remote.dto

data class PredictionResponse(
    val prediction: String,
    val probability0: Double,
    val probability1: Double
)