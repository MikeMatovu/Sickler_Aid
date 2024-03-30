package com.micodes.sickleraid.presentation.auth.signup

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.micodes.sickleraid.data.remote.DataProvider
import com.micodes.sickleraid.domain.model.Response
import com.micodes.sickleraid.presentation.auth.AuthViewModel
import com.micodes.sickleraid.presentation.common.composable.BasicButton
import com.micodes.sickleraid.presentation.common.composable.BasicToolbar
import com.micodes.sickleraid.presentation.common.composable.EmailField
import com.micodes.sickleraid.presentation.common.composable.PasswordField
import com.micodes.sickleraid.presentation.common.composable.RepeatPasswordField
import com.micodes.sickleraid.presentation.common.ext.basicButton
import com.micodes.sickleraid.presentation.common.ext.fieldModifier
import com.micodes.sickleraid.presentation.navgraph.Screen


@Composable
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit,
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    val currentUser = authViewModel.currentUser.collectAsState().value
    DataProvider.updateAuthState(currentUser)

    Log.i("AuthRepo", "Authenticated: ${DataProvider.isAuthenticated}")
    Log.i("AuthRepo", "Anonymous: ${DataProvider.isAnonymous}")
    Log.i("AuthRepo", "User: ${DataProvider.user}")

    SignUpScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onSignUpClick = {
            authViewModel.validateCredentialsAndCreateAccount(uiState.email, uiState.password, uiState.repeatPassword)
        }
    )

    //HERE
    when (val signUpResponse = DataProvider.createUserResponse) {
        is Response.Loading -> {
            Log.i("Create User:Email and password", "Loading")
            AuthLoginProgressIndicator()
        }

        is Response.Success -> signUpResponse.data?.let { authResult ->
            Log.i("Create User:Email and password", "Success: $authResult")
            navController.navigate(Screen.HomeScreen.route)
        }

        is Response.Failure -> {
            Log.e("Create User:Email and password", "${signUpResponse.e}")
        }
    }
    //HERE

}

@Composable
fun AuthLoginProgressIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.tertiary,
            strokeWidth = 5.dp
        )
    }
}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit
) {
    val fieldModifier = Modifier.fieldModifier()

    BasicToolbar("Create account")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(uiState.email, onEmailChange, fieldModifier)
        PasswordField(uiState.password, onPasswordChange, fieldModifier)
        RepeatPasswordField(uiState.repeatPassword, onRepeatPasswordChange, fieldModifier)

        BasicButton("Create account", Modifier.basicButton()) {
            onSignUpClick()
        }
    }
}