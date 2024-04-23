package com.micodes.sickleraid.presentation.doctor_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.micodes.sickleraid.presentation.common.composable.BasicField
import com.micodes.sickleraid.presentation.common.composable.CenterAlignedTopAppBarComposable
import com.micodes.sickleraid.presentation.doctor_detail.components.DoctorListItem
import com.micodes.sickleraid.presentation.navgraph.Screen

@Composable
fun DoctorListScreen(
    navController: NavController,
    viewModel: DoctorViewModel = hiltViewModel(),
) {

    val uiState by viewModel.state.collectAsState()
    val scrollState = rememberLazyListState()
    DoctorListScreenContent(
        navController = navController,
        uiState = uiState,
        onAddDoctorClick = viewModel::showDialog,
        onCancelDialog = viewModel::hideDialog,
        onSaveDoctor = viewModel::insertDoctor,
        onFirstNameChange = viewModel::onFirstNameChanged,
        onLastNameChange = viewModel::onLastNameChanged,
        onEmailChange = viewModel::onEmailChanged,
        onPhoneNumberChange = viewModel::onPhoneNumberChanged
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorListScreenContent(
    navController: NavController,
    uiState: DoctorUiState,
    onAddDoctorClick: () -> Unit,
    onSaveDoctor: () -> Unit,
    onCancelDialog: () -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            CenterAlignedTopAppBarComposable(
                title = "Doctors",
                scrollBehavior = scrollBehavior,
                profileImage = Icons.Default.Person,
                buttonText = "Save",
                onButtonClick = {},
                onBackPressed = { navController.navigateUp() } // Handle back navigation
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddDoctorClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {


        if (uiState.isInsertDoctorDialogVisible) {
            AlertDialog(
                onDismissRequest = onCancelDialog,
                title = { Text("Add Doctor") },
                text = {
                    Column {
                        BasicField(
                            text = "First Name",
                            value = uiState.firstName,
                            onNewValue = onFirstNameChange
                        )
                        BasicField(
                            text = "Last Name",
                            value = uiState.lastName,
                            onNewValue = onLastNameChange
                        )
                        BasicField(
                            text = "Email",
                            value = uiState.email,
                            onNewValue = onEmailChange
                        )
                        BasicField(
                            text = "Phone Number",
                            value = uiState.phoneNumber,
                            onNewValue = onPhoneNumberChange
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = onSaveDoctor) {
                        Text("Save")
                    }
                }
            )
        }

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                LazyColumn {
                    items(uiState.doctorList.size) { index ->
                        DoctorListItem(
                            doctor = uiState.doctorList[index],
                            onDoctorDetailsClick = {navController.navigate("doctor/${uiState.doctorList[index].id}")}
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
