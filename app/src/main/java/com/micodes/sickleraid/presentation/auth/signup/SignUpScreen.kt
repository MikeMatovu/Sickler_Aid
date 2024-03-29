package com.micodes.sickleraid.presentation.auth.signup

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.micodes.sickleraid.data.remote.AuthState
import com.micodes.sickleraid.data.remote.DataProvider
import com.micodes.sickleraid.domain.model.Response
import com.micodes.sickleraid.presentation.common.composable.*
import com.micodes.sickleraid.presentation.common.ext.basicButton
import com.micodes.sickleraid.presentation.common.ext.fieldModifier
import com.micodes.sickleraid.presentation.home.HomeScreen


@Composable
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    val currentUser = viewModel.currentUser.collectAsState().value
    DataProvider.updateAuthState(currentUser)

    Log.i("AuthRepo", "Authenticated: ${DataProvider.isAuthenticated}")
    Log.i("AuthRepo", "Anonymous: ${DataProvider.isAnonymous}")
    Log.i("AuthRepo", "User: ${DataProvider.user}")

    if (DataProvider.authState != AuthState.SignedOut) {
        HomeScreen(navController = rememberNavController())
    } else {
        SignUpScreenContent(
            uiState = uiState,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
            onSignUpClick = { viewModel.signInAnonymously()}
        )
    }
    //HERE
    when (val anonymousResponse = DataProvider.anonymousSignInResponse) {
        is Response.Loading -> {
            Log.i("Login:AnonymousSignIn", "Loading")
            AuthLoginProgressIndicator()
        }
        is Response.Success -> anonymousResponse.data?.let { authResult ->
            Log.i("Login:AnonymousSignIn", "Success: $authResult")
        }
        is Response.Failure -> {
            Log.e("Login:AnonymousSignIn", "${anonymousResponse.e}")
        }
    }
    //HERE

}

@Composable
fun AuthLoginProgressIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
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