package com.micodes.sickleraid.presentation.doctor_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
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
import coil.compose.rememberAsyncImagePainter
import com.micodes.sickleraid.R
import com.micodes.sickleraid.presentation.common.composable.ProfileAvatar
import com.micodes.sickleraid.presentation.common.composable.TopAppBarComposable
import com.micodes.sickleraid.presentation.doctor_detail.components.ActionButton
import com.micodes.sickleraid.presentation.doctor_detail.components.BoldHeading
import com.micodes.sickleraid.presentation.doctor_detail.components.ButtonRow
import com.micodes.sickleraid.presentation.doctor_detail.components.OverViewSection

@Composable
fun DoctorDetailsScreen(
    navController: NavController,
    viewModel: DoctorDetailViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    DoctorDetailContent(
        state = state,
        viewModel = viewModel

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorDetailContent(
    state: DoctorDetailsState,
    viewModel: DoctorDetailViewModel
) {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBarComposable(
                title = {
                    Text(text = "Doctor details")
                },
                actions = listOf {
                    IconButton(onClick = { /* TODO */ }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_app),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ) {paddingValues ->
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
            BoldHeading()
            Spacer(modifier = Modifier.height(16.dp))

            ButtonRow()
            Spacer(modifier = Modifier.height(64.dp))

            OverViewSection()

            Spacer(modifier = Modifier.weight(1f))
            ActionButton(
                onClick = {
                    viewModel.insertDoctor()
                }
            )


        }
    }
}

@Preview
@Composable
fun DoctorDetailsScreenPreview() {
    val navController = rememberNavController()
    DoctorDetailsScreen(navController = navController)
}