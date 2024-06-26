package com.micodes.sickleraid.presentation.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.micodes.sickleraid.R
import com.micodes.sickleraid.domain.repository.MedicineRepository
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
    private val medicineRepository: MedicineRepository,
    private val auth: FirebaseAuth,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    //TO add this in the state
    private val _allEducationalMaterials = MutableLiveData<List<SupportResource>>()
    val allEducationalMaterials: LiveData<List<SupportResource>> = _allEducationalMaterials

    private val _displayedEducationalMaterials = MutableLiveData<List<SupportResource>>()
    val displayedEducationalMaterials: LiveData<List<SupportResource>> = _displayedEducationalMaterials

    fun getPrediction(
        sn: Int,
        gender: Int,
        patientAge: Int,
        diagnosisAge: Int,
        bmi: Int,
        pcv: Int,
        crisisFrequency: Int,
        transfusionFrequency: Int,
        spo2: Int,
        systolicBP: Int,
        diastolicBP: Int,
        heartRate: Int,
        respiratoryRate: Int,
        hbF: Int,
        temp: Int,
        mcv: Int,
        platelets: Int,
        alt: Int,
        bilirubin: Int,
        ldh: Int,
        percentageAverage: Int
    ) {
        viewModelScope.launch {
            _state.value =
                state.value.copy(isPredicting = true)
            val prediction = repository.getPrediction(
                sn,
                gender,
                patientAge,
                diagnosisAge,
                bmi,
                pcv,
                crisisFrequency,
                transfusionFrequency,
                spo2,
                systolicBP,
                diastolicBP,
                heartRate,
                respiratoryRate,
                hbF,
                temp,
                mcv,
                platelets,
                alt,
                bilirubin,
                ldh,
                percentageAverage
            )
            _state.update {
                it.copy(
                    predictionState = prediction,
                    isPredicting = false
                )

            }
        }
    }

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
        getMedicineList()
    }

    //Functions
    fun getLatestPatientRecords() {
        viewModelScope.launch {
            val currentUser: FirebaseUser? = auth.currentUser
            currentUser?.let { firebaseUser ->
                val userId = firebaseUser.uid
                val latestRecord = repository.getLatestFirebasePatientRecords(userId)
                _state.update {
                    it.copy(
                        latestRecords = latestRecord,
                    )
                }
            }
        }
    }

    private fun getMedicineList() {
        viewModelScope.launch {
            val currentUser: FirebaseUser? = auth.currentUser
            currentUser?.let { firebaseUser ->
                val userId = firebaseUser.uid
                val medicineList = medicineRepository.getMedicines(userId)
                _state.value = state.value.copy(
                    medicineList = medicineList.map {
                        MedicineItem(
                            name = it.name,
                            quantity = it.id,
                        )
                    },
//                    isLoading = false
                )
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

            educationalMaterials.forEachIndexed { index, (title, url) ->
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

            _allEducationalMaterials.value = newSupportResources
            _displayedEducationalMaterials.value = newSupportResources.take(6)
            updateSupportResourcesList(newSupportResources)
        }
    }

    fun onViewAllClicked() {
        _displayedEducationalMaterials.value = _allEducationalMaterials.value
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
