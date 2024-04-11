package com.micodes.sickleraid.presentation.home

import com.micodes.sickleraid.R

data class HomeState(
    var latestRecords :String = "",
    var educationalMaterials: List<Pair<String, String>> = emptyList(),
    var supportResources: List<SupportResource> = listOf(
        SupportResource(
            title = "",
            imageResourceId = R.drawable.resource_img_1,
            url = ""
        ),
        SupportResource(
            title = "",
            imageResourceId = R.drawable.resource_img_2,
            url = ""
        ),
        SupportResource(
            title = "",
            imageResourceId = R.drawable.resource_img_3,
            url = ""
        ),
        SupportResource(
            title = "",
            imageResourceId = R.drawable.resource_img_4,
            url = ""
        ),
        SupportResource(
            title = "",
            imageResourceId = R.drawable.resource_img_5,
            url = ""
        ),
        SupportResource(
            title = "",
            imageResourceId = R.drawable.resource_img_6,
            url = ""
        ),

    ),
    var isLoading: Boolean = false
)