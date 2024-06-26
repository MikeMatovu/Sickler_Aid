package com.micodes.sickleraid.presentation.daily_checkup

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.micodes.sickleraid.data.remote.DataProvider
import com.micodes.sickleraid.domain.model.Response
import com.micodes.sickleraid.presentation.common.composable.BasicButton
import com.micodes.sickleraid.presentation.common.composable.CenterAlignedTopAppBarComposable
import com.micodes.sickleraid.presentation.common.composable.DismissDialogComposable
import com.micodes.sickleraid.presentation.common.composable.ProgressIndicatorComposable
import com.micodes.sickleraid.presentation.common.composable.UnderlineTextField
import com.micodes.sickleraid.presentation.profile.composable.SpaceVertical32

@Composable
fun DailyCheckupScreen(
    navController: NavController,
    viewModel: DailyCheckupViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    DailyCheckupScreenContent(
        uiState = state,
        navController = navController,
        openDialog = viewModel::openDialog,
        onDismissDialog = viewModel::onDialogDismiss,
        onChangeTemperature = viewModel::onChangeTemperature,
        onChangeSystolicBloodPressure = viewModel::onChangeSystolicBloodPressure,
        onChangeDiastolicBloodPressure = viewModel::onChangeDiastolicBloodPressure,
        onChangePulseRate = viewModel::onChangePulseRate,
        onChangeRespiratoryRate = viewModel::onChangeRespiratoryRate,
        onSubmitCheckup = viewModel::submitCheckup
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyCheckupScreenContent(
    uiState: DailyCheckupState,
    navController: NavController,
    openDialog: () -> Unit ,
    onDismissDialog: () -> Unit,
    onChangeTemperature: (Int) -> Unit,
    onChangeSystolicBloodPressure: (Int) -> Unit,
    onChangeDiastolicBloodPressure: (Int) -> Unit,
    onChangePulseRate: (Int) -> Unit,
    onChangeRespiratoryRate: (Int) -> Unit,
    onSubmitCheckup: () -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            CenterAlignedTopAppBarComposable(
                title = "Daily Checkup",
                scrollBehavior = scrollBehavior,
                profileImage = Icons.Default.Person,
                buttonText = "Save",
                onButtonClick = {},
                onBackPressed = { navController.navigateUp() } // Handle back navigation
            )
        }
    ) {
        Column(

            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                when {
                    uiState.openAlertDialog -> {
                        DismissDialogComposable(
                            onDismissRequest = onDismissDialog,
                            dialogTitle = "Daily Checkup",
                            dialogText = "Saved Daily Checkup",
                            icon = Icons.Default.Info
                        )
                    }
                }
                Text(
                    "Last updated on ${uiState.lastModifiedDateTime}",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    "Keep track of your daily physiological measures. This information will be used to monitor your health.",
                    style = MaterialTheme.typography.bodySmall
                )
                UnderlineTextField(
                    value = uiState.temperature,
                    label = "Temperature",
                    onValueChange = onChangeTemperature
                )
                UnderlineTextField(
                    value = uiState.systolicBloodPressure,
                    label = "Systolic Blood Pressure",
                    onValueChange = onChangeSystolicBloodPressure
                )
                UnderlineTextField(
                    value = uiState.diastolicBloodPressure,
                    label = "Diastolic Blood Pressure",
                    onValueChange = onChangeDiastolicBloodPressure
                )
                UnderlineTextField(
                    value = uiState.pulseRate,
                    label = "Pulse Rate",
                    onValueChange = onChangePulseRate
                )
                UnderlineTextField(
                    value = uiState.respiratoryRate,
                    label = "Respiratory Rate",
                    onValueChange = onChangeRespiratoryRate
                )

                SpaceVertical32()
                BasicButton(
                    text = "SUBMIT",
                    modifier = Modifier.fillMaxWidth(),
                    action = onSubmitCheckup
                )

                when(val databaseOperationResponse = DataProvider.databaseOperationResponse) {
                    // 1.
                    is Response.Loading ->  {
                        Log.i("Database operation", "Loading")
                        ProgressIndicatorComposable()
                    }
                    // 2.
                    is Response.Success -> databaseOperationResponse.data?.let { operationResult ->
                        LaunchedEffect(operationResult) {
                            Log.i("Operation:Database", "Success")
                            openDialog()
                        }
                    }
                    is Response.Failure -> LaunchedEffect(Unit) {
                        Log.e("Operation:Database", "${databaseOperationResponse.e}")
                    }
                    else -> {}
                }

            }
        }
    }

}