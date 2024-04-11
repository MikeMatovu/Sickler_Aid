package com.micodes.sickleraid.presentation.home

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.painter.Painter

data class SupportResource(
    val title: String,
    @DrawableRes val imageResourceId: Int,
    val url: String
)
