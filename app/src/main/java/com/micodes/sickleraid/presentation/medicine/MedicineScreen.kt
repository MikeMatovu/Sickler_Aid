package com.micodes.sickleraid.presentation.medicine

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import com.micodes.sickleraid.presentation.common.composable.CenterAlignedTopAppBarComposable
import com.micodes.sickleraid.presentation.common.composable.DismissDialogComposable
import com.micodes.sickleraid.presentation.common.composable.ProgressIndicatorComposable
import com.micodes.sickleraid.presentation.medicine.components.MedicineCard
import java.time.LocalTime

@Composable
fun MedicineScreen(
    navController: NavController,
    viewModel: MedicineViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    val scrollState = rememberLazyListState()

    LaunchedEffect(viewModel.medicineListSize) {
        viewModel.refresh()
    }
    LaunchedEffect(viewModel.scrollToNewItem) {
        // Scroll to the bottom when a new item is added
        scrollState.scrollToItem(uiState.medicineList.size)
    }

    MedicineScreenContent(
        navController = navController,
        uiState = uiState,
        scrollState = scrollState,
        openDialog = viewModel::openDialog,
        onDismissDialog = viewModel::onDialogDismiss,
        onFrequencyChanged = viewModel::onFrequencyChanged,
        onDosageChanged = viewModel::onDosageChanged,
        onNameChanged = viewModel::onNameChanged,
        onCreateNewMedicine = viewModel::createNewMedicine,
        onEditClicked = viewModel::onEditClicked,
        onCancelClicked = viewModel::onCancelClicked,
        onSavedClicked = viewModel::onSavedClicked,
        onReminderSet = viewModel::onReminderSet,
        onOpenReminder = viewModel::onOpenReminder,
        onCloseTimeDialog = viewModel::onCloseReminder
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineScreenContent(
    navController: NavController,
    uiState: MedicineUiState,
    scrollState: LazyListState,
    openDialog: () -> Unit,
    onDismissDialog: () -> Unit,
    onFrequencyChanged: (Int, String) -> Unit,
    onDosageChanged: (Int, String) -> Unit,
    onNameChanged: (Int, String) -> Unit,
    onCreateNewMedicine: () -> Unit,
    onEditClicked: (Int) -> Unit,
    onCancelClicked: (Int) -> Unit,
    onSavedClicked: (Int, Int) -> Unit,
    onReminderSet: (Int, LocalTime) -> Unit,
    onOpenReminder: (Int) -> Unit,
    onCloseTimeDialog: (Int) -> Unit
) {


    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            CenterAlignedTopAppBarComposable(
                title = "Medicine Tracker",
                scrollBehavior = scrollBehavior,
                profileImage = Icons.Default.Person,
                buttonText = "Save",
                onButtonClick = {},
                onBackPressed = { navController.navigateUp() } // Handle back navigation
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateNewMedicine) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                if (uiState.isLoading) {
                    ProgressIndicatorComposable()
                } else {
                    when {
                        uiState.openAlertDialog -> {
                            DismissDialogComposable(
                                onDismissRequest = onDismissDialog,
                                dialogTitle = "Medicine",
                                dialogText = "An error occurred try again later",
                                icon = Icons.Default.Info
                            )
                        }
                    }
                    LazyColumn(
                        state = scrollState
                    ) {

                        items(uiState.medicineList.size) { index ->
                            val medicine = uiState.medicineList[index]
                            MedicineCard(
                                modifier = Modifier.padding(8.dp),
                                medicine = medicine,
                                onNameChanged = { onNameChanged(index, it) },
                                onDosageChanged = { onDosageChanged(index, it) },
                                onFrequencyChanged = { onFrequencyChanged(index, it) },
                                onEditClicked = { onEditClicked(index) },
                                onCancelClicked = { onCancelClicked(index) },
                                onSaveClicked = { onSavedClicked(index, medicine.id) },
                                onReminderSet = { time -> onReminderSet(index, time) },
                                onOpenReminder = {onOpenReminder(index)},
                                onCloseTimeDialog = {onCloseTimeDialog(index)},
                                onMarkAsTaken = { /* handle mark as taken */ }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            when (val databaseOperationResponse =
                                DataProvider.databaseOperationResponse) {
                                // 1.
                                is Response.Loading -> {
                                    Log.i("Database operation", "Loading")
                                    ProgressIndicatorComposable()
                                }
                                // 2.
                                is Response.Success -> databaseOperationResponse.data?.let { operationResult ->
                                    LaunchedEffect(operationResult) {
                                        Log.i("Operation:Database", "Success")
                                    }
                                }

                                is Response.Failure -> LaunchedEffect(Unit) {
                                    Log.e("Operation:Database", "${databaseOperationResponse.e}")
                                    openDialog()
                                }

                                else -> {}
                            }
                        }
                    }


                }

            }
        }
    }


}

@Composable
@Preview(showBackground = true)
fun MedicineScreenPreview() {
    MedicineScreen(navController = rememberNavController())
}
