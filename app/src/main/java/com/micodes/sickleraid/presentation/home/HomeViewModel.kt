package com.micodes.sickleraid.presentation.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.micodes.sickleraid.R
import com.micodes.sickleraid.domain.repository.SicklerAidRepository
import com.micodes.sickleraid.util.RESOURCES_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SicklerAidRepository,
    private val auth: FirebaseAuth,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    val resourceIds = ResourceIds(
        listOf(
            R.drawable.resource_img_1,
            R.drawable.resource_img_2,
            R.drawable.resource_img_3,
            R.drawable.resource_img_4,
            R.drawable.resource_img_5,
            R.drawable.resource_img_6,
        )
    )


    init {
        getLatestPatientRecords()
        getEducationalMaterials()
    }

    //Functions
    fun getLatestPatientRecords() {
        viewModelScope.launch {
            val currentUser: FirebaseUser? = auth.currentUser
            currentUser?.let { firebaseUser ->
                val userId = firebaseUser.uid
                val latestRecord = repository.getLatestPatientRecords(userId)
                _state.update { it.copy(latestRecords = latestRecord.toString()) }
            }
        }
    }

    private fun getEducationalMaterials() {
        viewModelScope.launch {
            val newSupportResources = mutableListOf<SupportResource>()

            // Copy existing support resources from state
            newSupportResources.addAll(_state.value.supportResources)


            _state.value =
                state.value.copy(isLoading = true) // Set isLoading to true before loading data
            val htmlContent = repository.fetchHtmlContent(RESOURCES_URL)
            val educationalMaterials = repository.scrapeEducationalMaterials(htmlContent)
            _state.update {
                it.copy(
                    educationalMaterials = educationalMaterials,
                    isLoading = false // Set isLoading to false after loading data
                )
            }
            // Update the supportResources list with the title and url of the first 6 educational materials. Do not update the imageResourceId


            educationalMaterials.take(6).forEachIndexed { index, (title, url) ->
                // Check if there's already an existing SupportResource at this index
                if (index < newSupportResources.size) {
                    // Use the existing imageResourceId from the state
                    val existingResource = newSupportResources[index]
                    newSupportResources[index] = existingResource.copy(
                        title = title,
                        url = url
                    )
                } else {
                    // If there's no existing SupportResource at this index, create a new one
                    val randomResourceId = getRandomResourceId(resourceIds)
                    newSupportResources.add(
                        SupportResource(
                            title = title,
                            imageResourceId = randomResourceId, // Use a placeholder or default image resource ID
                            url = url
                        )
                    )
                }
            }

            updateSupportResourcesList(newSupportResources)
        }
    }


    private fun updateSupportResourcesList(supportResources: List<SupportResource>) {
        _state.update { it.copy(supportResources = supportResources) }
    }


    fun onResourceClicked(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    private fun getRandomResourceId(resourceIds: ResourceIds): Int {
        val randomIndex = (0 until resourceIds.resourceIds.size).random()
        return resourceIds.resourceIds[randomIndex]
    }

}


data class ResourceIds(
    val resourceIds: List<Int>
)
