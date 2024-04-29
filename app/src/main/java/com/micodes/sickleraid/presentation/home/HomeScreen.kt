package com.micodes.sickleraid.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.micodes.sickleraid.R
import com.micodes.sickleraid.data.remote.DataProvider
import com.micodes.sickleraid.domain.model.FirebaseMedicalRecord
import com.micodes.sickleraid.domain.model.MedicalRecords
import com.micodes.sickleraid.presentation.auth.AuthViewModel
import com.micodes.sickleraid.presentation.common.composable.ProgressIndicatorComposable
import com.micodes.sickleraid.presentation.common.composable.TopAppBarComposable
import com.micodes.sickleraid.presentation.home.components.ContentRow
import com.micodes.sickleraid.presentation.home.components.MedicineRow
import com.micodes.sickleraid.presentation.home.components.ResourcesRow
import com.micodes.sickleraid.presentation.home.components.SectionTitle
import com.micodes.sickleraid.presentation.main_activity.MainViewModel
import com.micodes.sickleraid.presentation.navgraph.Screen

//TODO: Move auth viewmodel to main viewmodel
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()

) {

    val context = LocalContext.current
    val state by homeViewModel.state.collectAsState()
    val latestDailyCheckup = state.latestRecords?.latestDailyCheckup
    val latestMedicalRecords = state.latestRecords?.latestMedicalRecord


    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val authState = DataProvider.authState

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBarComposable(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_app), //TODO: add image
                        contentDescription = "Logo",
                        modifier = Modifier.fillMaxWidth(0.4f)
                    )
                },
                actions = listOf(
                    {
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = null
                            )
                        }
                    },
                    {
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = null
                            )
                        }
                    },
                    {
                        IconButton(onClick = {
                            navController.navigate(Screen.Profile.route)
                        }) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_profile),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                            )
                        }
                    }
                )
            )
        },
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                SectionTitle(
                    sectionTitle = "Medical Information",
                    buttonTitle = "Get Latest"
                ) {
                    homeViewModel.getLatestPatientRecords()
                    if (latestDailyCheckup != null) {
                        when (latestMedicalRecords) {
                            is MedicalRecords -> {
                                homeViewModel.getPrediction(
                                    sn = latestMedicalRecords.bmi,
                                    gender = latestMedicalRecords.aat,
                                    patientAge = 40,
                                    diagnosisAge = 3,
                                    bmi = latestMedicalRecords.bmi,
                                    pcv = latestMedicalRecords.packetCellVolume,
                                    crisisFrequency = 6,
                                    transfusionFrequency = latestMedicalRecords.ldh,
                                    spo2 = latestDailyCheckup.pulseRate,
                                    systolicBP = latestDailyCheckup.systolicBP,
                                    diastolicBP = latestDailyCheckup.diastolicBP,
                                    heartRate = latestDailyCheckup.respiratoryRate,
                                    respiratoryRate = latestDailyCheckup.respiratoryRate,
                                    hbF = latestMedicalRecords.fetalHaemoglobin,
                                    temp = latestDailyCheckup.temperature,
                                    mcv = latestMedicalRecords.meanCorpuscularVolume,
                                    platelets = latestMedicalRecords.platelets,
                                    alt = latestMedicalRecords.aat,
                                    bilirubin = latestMedicalRecords.birulubin,
                                    ldh = latestMedicalRecords.ldh,
                                    percentageAverage = 60
                                )
                            }
                            is FirebaseMedicalRecord -> {
                                homeViewModel.getPrediction(
                                    sn = latestMedicalRecords.bmi,
                                    gender = latestMedicalRecords.aat,
                                    patientAge = 40,
                                    diagnosisAge = 3,
                                    bmi = latestMedicalRecords.bmi,
                                    pcv = latestMedicalRecords.packetCellVolume,
                                    crisisFrequency = 6,
                                    transfusionFrequency = latestMedicalRecords.ldh,
                                    spo2 = latestDailyCheckup.pulseRate,
                                    systolicBP = latestDailyCheckup.systolicBP,
                                    diastolicBP = latestDailyCheckup.diastolicBP,
                                    heartRate = latestDailyCheckup.respiratoryRate,
                                    respiratoryRate = latestDailyCheckup.respiratoryRate,
                                    hbF = latestMedicalRecords.fetalHaemoglobin,
                                    temp = latestDailyCheckup.temperature,
                                    mcv = latestMedicalRecords.meanCorpuscularVolume,
                                    platelets = latestMedicalRecords.platelets,
                                    alt = latestMedicalRecords.aat,
                                    bilirubin = latestMedicalRecords.birulubin,
                                    ldh = latestMedicalRecords.ldh,
                                    percentageAverage = 60
                                )
                            }
                            null -> TODO()
                        }
                    }
                }
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    ContentRow(
                        title = "Medical records",
                        onButtonClick = {
                            navController.navigate(Screen.MedicalRecords.route)
                        },
                        modifier = Modifier
                    )
                    if (state.isPredicting) {
                        ProgressIndicatorComposable()
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    ContentRow(
                        title = "Daily Checkup",
                        onButtonClick = {
                            navController.navigate(Screen.DailyCheckup.route)
                        },
                        modifier = Modifier
                    )
                }
            }
            item {

                SectionTitle(
                    sectionTitle = "Medication List",
                    buttonTitle = "View All"
                ) {
                    //TODO: FIX NAVIGATION BUG HERE
                    navController.navigate(Screen.Medicine.route)
                }


                if (state.isLoading) {
                    ProgressIndicatorComposable()
                } else {
                    MedicineRow(medicines = state.medicineList, onMedicineClick = {})
                }
            }

            item {

                SectionTitle(
                    sectionTitle = "My support",
                    buttonTitle = "View All"
                ) {
                    homeViewModel.onViewAllClicked()
                }

                if (state.isLoading) {
                    ProgressIndicatorComposable()
                } else {
                    homeViewModel.displayedEducationalMaterials.value?.let {
                        ResourcesRow(resources = it) { url ->
                            // Handle resource click
                            homeViewModel.onResourceClicked(context, url)
                        }
                    }
                }

            }


            item {
                //End dummy content
//
//                if (authState == AuthState.SignedIn) {
//                    Text(
//                        text = DataProvider.user?.displayName ?: "Name Placeholder",
//                        fontWeight = FontWeight.Bold
//                    )
//                    Text(DataProvider.user?.email ?: "Email Placeholder")
//                } else {
//                    Text(
//                        "Sign-in to view data!"
//                    )
//                }
//                Button(
//                    onClick = {
//
//                    },
//                ) {
//                    Text(
//                        text = if (authState != AuthState.SignedIn) "Sign-in" else "Sign out",
//                        modifier = Modifier.padding(6.dp),
//                    )
//                }


                if (state.isPredicting) {
                    ProgressIndicatorComposable()
                } else {
//                    state.predictionState?.let { predictionResponse ->
//                        // Handle prediction response here
//                        Text("This is the prediction: ${predictionResponse.prediction}")
//                    }
                }


                //End dummy content
            }


        }


    }

}


@Composable
@Preview
fun HomeScreenPreview() {
//    HomeScreen(navController = rememberNavController())

}

