package com.micodes.sickleraid.presentation.home

import androidx.annotation.DrawableRes

data class SupportResource(
    val title: String,
    @DrawableRes val imageResourceId: Int,
    val url: String
)

data class MedicineItem(
    val name: String,
    val quantity: Int
)
