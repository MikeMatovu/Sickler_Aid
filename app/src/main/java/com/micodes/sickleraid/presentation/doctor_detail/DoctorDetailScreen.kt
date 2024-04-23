package com.micodes.sickleraid.presentation.doctor_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.micodes.sickleraid.R
import com.micodes.sickleraid.presentation.common.composable.CenterAlignedTopAppBarComposable
import com.micodes.sickleraid.presentation.common.composable.ProfileAvatar
import com.micodes.sickleraid.presentation.common.composable.ProgressIndicatorComposable
import com.micodes.sickleraid.presentation.doctor_detail.components.BoldHeading
import com.micodes.sickleraid.presentation.doctor_detail.components.ButtonRow
import com.micodes.sickleraid.presentation.doctor_detail.components.EmergencyActionButton
import com.micodes.sickleraid.presentation.doctor_detail.components.OverViewSection

@Composable
fun DoctorDetailsScreen(
    doctorId: String?,
    navController: NavController,
    viewModel: DoctorViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(doctorId) {
        viewModel.getDoctorById(doctorId?.toInt())
    }



    if (state.isLoading) {
        ProgressIndicatorComposable()
    } else {
        DoctorDetailContent(
            state = state,
            navController = navController,
            viewModel = viewModel

        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorDetailContent(
    state: DoctorUiState,
    navController: NavController,
    viewModel: DoctorViewModel
) {
    val doctor = state.doctor
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBarComposable(
                title = "Doctor Details",
                scrollBehavior = scrollBehavior,
                profileImage = Icons.Default.Person,
                buttonText = "Save",
                onButtonClick = {},
                onBackPressed = { navController.navigateUp() } // Handle back navigation
            )
        }
    ) { paddingValues ->
        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileAvatar(
//                painter = rememberAsyncImagePainter(model = state.profilePictureLink),
                painter = painterResource(id = R.drawable.onboard_image),
                size = 128
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (doctor != null) {
                BoldHeading(
                    name = doctor.firstName + " " + doctor.lastName
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            ButtonRow(
                onAppointmentClick = { /*TODO*/ },
                onSendReportClick = { /*TODO*/ },
            )
            Spacer(modifier = Modifier.height(64.dp))

            OverViewSection(
                overview = "This is the page with details about doctor. " +
                        "You can see the doctor's name, profile picture, and other details here."
            )

            Spacer(modifier = Modifier.weight(1f))
            EmergencyActionButton(
                onEmergencyClick = { /*TODO*/
                }
            )


        }
    }
}

@Preview
@Composable
fun DoctorDetailsScreenPreview() {
    val navController = rememberNavController()
    DoctorDetailsScreen(doctorId = "", navController = navController)
}