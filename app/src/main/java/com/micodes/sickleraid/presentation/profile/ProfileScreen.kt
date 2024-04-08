package com.micodes.sickleraid.presentation.profile

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.micodes.sickleraid.data.remote.DataProvider
import com.micodes.sickleraid.domain.model.Response
import com.micodes.sickleraid.presentation.auth.AuthViewModel
import com.micodes.sickleraid.presentation.auth.signup.AuthLoginProgressIndicator
import com.micodes.sickleraid.presentation.common.composable.BasicButton
import com.micodes.sickleraid.presentation.common.composable.BasicField
import com.micodes.sickleraid.presentation.common.composable.CenterAlignedTopAppBarComposable
import com.micodes.sickleraid.presentation.common.composable.ProfileAvatar
import com.micodes.sickleraid.presentation.common.composable.ProgressIndicatorComposable
import com.micodes.sickleraid.presentation.common.ext.basicButton
import com.micodes.sickleraid.presentation.navgraph.Screen
import com.micodes.sickleraid.presentation.profile.composable.Header
import com.micodes.sickleraid.presentation.profile.composable.SpaceVertical24
import com.micodes.sickleraid.presentation.profile.composable.SpaceVertical32
import com.micodes.sickleraid.presentation.profile.composable.TextButton

@Composable
fun ProfileScreen(
    navController: NavController,
    mainNavController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    ProfileContent(
        uiState = state,
        authViewModel = authViewModel,
        viewModel = viewModel,
        navController = navController,
        mainNavController = mainNavController,
        onChangeFirstName = viewModel::onChangeFirstName,
        onChangeLastName = viewModel::onChangeLastName,
        onChangeEmail = viewModel::onChangeEmail,
        onChangePhone = viewModel::onChangePhone,
        onSaveUserInfo = viewModel::onSaveUserInfo
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileContent(
    uiState: ProfileUiState,
    authViewModel: AuthViewModel,
    viewModel: ProfileViewModel,
    navController: NavController,
    mainNavController: NavController,
    onChangeFirstName: (String) -> Unit,
    onChangeLastName: (String) -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangePhone: (String) -> Unit,
    onSaveUserInfo: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            CenterAlignedTopAppBarComposable(
                title = "Profile",
                scrollBehavior = scrollBehavior,
                profileImage = Icons.Default.Person,
                buttonText = "log out",
                onButtonClick = {
                    authViewModel.signOut()
                    mainNavController.navigate(Screen.OnBoardingNavigation.route) {
                        popUpTo(Screen.AppNavigation.route) {
                            inclusive = true
                        }
                    }
                },
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }
    ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(it)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(
            title = "Account",
            subtitle = "Edit your profile information",
        )
        SpaceVertical32()

        ProfileAvatar(
            painter = rememberAsyncImagePainter(model = uiState.profilePictureLink),

            size = 128
        )
        SpaceVertical24()

        TextButton(text = "Change profile Picture") {}
        SpaceVertical32()

        BasicField(
            modifier = Modifier.padding(8.dp),
            text = "First Name",
            value = uiState.firstName,
            onNewValue = onChangeFirstName
        )
        BasicField(
            modifier = Modifier.padding(8.dp),
            text = "Last Name",
            value = uiState.lastName,
            onNewValue = onChangeLastName
        )
        BasicField(
            modifier = Modifier.padding(8.dp),
            text = "Email",
            value = uiState.email,
            onNewValue = onChangeEmail
        )
        BasicField(
            modifier = Modifier.padding(8.dp),
            text = "Phone Number",
            value = uiState.phone,
            onNewValue = onChangePhone
        )

        Spacer(modifier = Modifier.weight(1f))
        BasicButton(
            "UPDATE PROFILE",
            Modifier.basicButton()
        ) {
            onSaveUserInfo()
        }
        when(val databaseOperationResponse = DataProvider.databaseOperationResponse) {
            // 1.
            is Response.Loading ->  {
                Log.i("Database op:OneTap", "Loading")
                ProgressIndicatorComposable()
            }
            // 2.
            is Response.Success -> databaseOperationResponse.data?.let { operationResult ->
                LaunchedEffect(operationResult) {
//                    launch(operationResult)
                    Log.i("Operation:Database", "Success")
                    snackbarHostState.showSnackbar("Operation successful")
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

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
//    ProfileScreen(navController = rememberNavController())
}

