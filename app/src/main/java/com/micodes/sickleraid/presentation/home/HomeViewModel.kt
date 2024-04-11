package com.micodes.sickleraid.presentation.home

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


            updateSupportResourcesList(educationalMaterials.take(6).map { (title, url) ->
                SupportResource(
                    title = title,
                    imageResourceId = R.drawable.resource_img_6,
                    url = url
                )
            })


        }
    }

    private fun updateSupportResourcesList(supportResources: List<SupportResource>) {
        _state.update { it.copy(supportResources = supportResources) }
    }




}