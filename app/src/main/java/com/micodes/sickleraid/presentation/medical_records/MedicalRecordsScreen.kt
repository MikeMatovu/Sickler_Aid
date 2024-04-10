package com.micodes.sickleraid.presentation.medical_records

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.micodes.sickleraid.data.remote.DataProvider
import com.micodes.sickleraid.domain.model.Response
import com.micodes.sickleraid.presentation.common.composable.BasicButton
import com.micodes.sickleraid.presentation.common.composable.CenterAlignedTopAppBarComposable
import com.micodes.sickleraid.presentation.common.composable.DismissDialogComposable
import com.micodes.sickleraid.presentation.common.composable.ProgressIndicatorComposable
import com.micodes.sickleraid.presentation.common.composable.UnderlineTextField
import com.micodes.sickleraid.presentation.profile.composable.SpaceVertical32

@Composable
fun MedicalRecordsScreen(
    navController: NavController,
    viewModel: MedicalRecordsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    MedicalRecordsScreenContent(
        uiState = state,
        navController = navController,
        onSaveClick = viewModel::saveRecords,
        openDialog = viewModel::openDialog,
        onDismissDialog = viewModel::onDialogDismiss,
        onWeightChange = viewModel::onChangeWeight,
        onBirulubinChange = viewModel::onChangeBirulubin,
        onBMIChange = viewModel::onChangeBMI,
        onPlateletsChange = viewModel::onChangePlatelets,
        onPCVChange = viewModel::onChangePacketCellVolume,
        onFetalHaemoglobinChange = viewModel::onChangeFetalHaemoglobin,
        onMeanCorpuscularVolumeChange = viewModel::onChangeMeanCorpuscularVolume,
        onAlanineAminotransferaseChange = viewModel::onChangeAlanineAminotransferase,
        onLactateDehydrogenaseChange = viewModel::onChangeLactateDehydrogenase,
        onPeripheralCapillarityChange = viewModel::onChangePeripheralCapillarity
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicalRecordsScreenContent(
    uiState: MedicalRecordsState,
    navController: NavController,
    onSaveClick: () -> Unit,
    openDialog: () -> Unit ,
    onDismissDialog: () -> Unit,
    onBirulubinChange: (Int) -> Unit,
    onWeightChange: (Int) -> Unit,
    onBMIChange: (Int) -> Unit,
    onPlateletsChange: (Int) -> Unit,
    onPCVChange: (Int) -> Unit,
    onFetalHaemoglobinChange: (Int) -> Unit,
    onMeanCorpuscularVolumeChange: (Int) -> Unit,
    onAlanineAminotransferaseChange: (Int) -> Unit,
    onLactateDehydrogenaseChange: (Int) -> Unit,
    onPeripheralCapillarityChange: (Int) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            CenterAlignedTopAppBarComposable(
                title = "Medical Records",
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
                            dialogTitle = "Medical records",
                            dialogText = "Saved Medical Records",
                            icon = Icons.Default.Info
                        )
                    }
                }
                Text(
                    text = "Medical records",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    "Last updated on ${uiState.lastModifiedDateTime}",
                    style = MaterialTheme.typography.bodyMedium
                )
                SpaceVertical32()
                UnderlineTextField(
                    value = uiState.bmi,
                    label = "BMI",
                    onValueChange = onBMIChange,
                )
                UnderlineTextField(
                    value = uiState.weight,
                    label = "Weight",
                    onValueChange = onWeightChange,
                )

                UnderlineTextField(
                    value = uiState.packetCellVolume,
                    label = "Packet cell volume",
                    onValueChange = onPCVChange,
                )

                UnderlineTextField(
                    value = uiState.peripheralCapillarity,
                    label = "Peripheral capillarity",
                    onValueChange = onPeripheralCapillarityChange,
                )

                UnderlineTextField(
                    value = uiState.platelets,
                    label = "Platelets",
                    onValueChange = onPlateletsChange,
                )

                UnderlineTextField(
                    value = uiState.birulubin,
                    label = "Birulubin",
                    onValueChange = onBirulubinChange,
                )
                UnderlineTextField(
                    value = uiState.lactateDehydrogenase,
                    label = "Lactate Dehydrogenase (LDH)",
                    onValueChange = onLactateDehydrogenaseChange,
                )
                UnderlineTextField(
                    value = uiState.fetalHaemoglobin,
                    label = "Fetal Haemoglobin",
                    onValueChange = onFetalHaemoglobinChange,
                )
                UnderlineTextField(
                    value = uiState.meanCorpuscularVolume,
                    label = "Mean Corpuscular Volume",
                    onValueChange = onMeanCorpuscularVolumeChange,
                )
                UnderlineTextField(
                    value = uiState.alanineAminotransferase,
                    label = "Alanine Amino transferase",
                    onValueChange = onAlanineAminotransferaseChange,
                )


                SpaceVertical32()
                BasicButton(
                    text = "SAVE",
                    modifier = Modifier.fillMaxWidth(),
                    action = onSaveClick
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

@Preview(showBackground = true)
@Composable
fun MedicalRecordsScreenPreview() {
    MedicalRecordsScreen(navController = rememberNavController())
}