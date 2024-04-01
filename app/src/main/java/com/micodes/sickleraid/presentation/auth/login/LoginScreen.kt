package com.micodes.sickleraid.presentation.auth.login

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GppGood
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException
import com.micodes.sickleraid.data.remote.DataProvider
import com.micodes.sickleraid.domain.model.Response
import com.micodes.sickleraid.presentation.auth.AuthViewModel
import com.micodes.sickleraid.presentation.common.composable.BasicButton
import com.micodes.sickleraid.presentation.common.composable.BasicTextButton
import com.micodes.sickleraid.presentation.common.composable.BasicToolbar
import com.micodes.sickleraid.presentation.common.composable.EmailField
import com.micodes.sickleraid.presentation.common.composable.PasswordField
import com.micodes.sickleraid.presentation.common.ext.basicButton
import com.micodes.sickleraid.presentation.common.ext.fieldModifier
import com.micodes.sickleraid.presentation.common.ext.textButton
import com.micodes.sickleraid.presentation.auth.signup.AuthLoginProgressIndicator
import com.micodes.sickleraid.presentation.auth.signup.SignUpViewModel
import com.micodes.sickleraid.presentation.common.composable.GoogleSignInButton
import com.micodes.sickleraid.presentation.navgraph.Screen


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                // 2.
                val credentials = authViewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                authViewModel.signInWithGoogle(credentials)
            }
            catch (e: ApiException) {
                Log.e("LoginScreen:Launcher","Login One-tap $e")
            }
        }
        else if (result.resultCode == Activity.RESULT_CANCELED){
            Log.e("LoginScreen:Launcher","OneTapClient Canceled")
        }
    }

    fun launch(signInResult: BeginSignInResult) {
        // 3.
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }

    LoginScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = { authViewModel.validateCredentialsAndSignIn(uiState.email, uiState.password) },
        onForgotPasswordClick = viewModel::onForgotPasswordClick,
        onGoogleSignInClick = authViewModel::oneTapSignIn
    )
    when(val oneTapSignInResponse = DataProvider.oneTapSignInResponse) {
        // 1.
        is Response.Loading ->  {
            Log.i("Login:OneTap", "Loading")
            AuthLoginProgressIndicator()
        }
        // 2.
        is Response.Success -> oneTapSignInResponse.data?.let { signInResult ->
            LaunchedEffect(signInResult) {
                launch(signInResult)
            }
        }
        is Response.Failure -> LaunchedEffect(Unit) {
            Log.e("Login:OneTap", "${oneTapSignInResponse.e}")
        }

        else -> {}
    }

    when (val signInWithGoogleResponse = DataProvider.googleSignInResponse) {
        // 1.
        is Response.Loading -> {
            Log.i("Login:GoogleSignIn", "Loading")
            AuthLoginProgressIndicator()
        }
        // 3.
        is Response.Success -> signInWithGoogleResponse.data?.let { authResult ->
            Log.i("Login:GoogleSignIn", "Success: $authResult")
            viewModel.loginState.value = false
            navController.navigate(Screen.HomeScreen.route)

        }
        is Response.Failure -> {
            Log.e("Login:GoogleSignIn", "${signInWithGoogleResponse.e}")
        }

        else -> {}
    }

    when (val logInResponse = DataProvider.signInWithEmailResponse) {
        is Response.Loading -> {
            Log.i("Create User:Email and password", "Loading")
            AuthLoginProgressIndicator()
        }

        is Response.Success -> logInResponse.data?.let { authResult ->
            Log.i("Login", "Success: $authResult")
            navController.navigate(Screen.HomeScreen.route)
        }

        is Response.Failure -> {
            Log.e("Create User:Email and password", "${logInResponse.e}")
        }

        else -> {}
    }
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onGoogleSignInClick: () -> Unit
) {
    BasicToolbar("Login details")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(uiState.email, onEmailChange, Modifier.fieldModifier())
        PasswordField(uiState.password, onPasswordChange, Modifier.fieldModifier())

        BasicButton("Sign in", Modifier.basicButton()) { onSignInClick() }

        BasicTextButton("Forgot password", Modifier.textButton()) {
            onForgotPasswordClick()
        }
        GoogleSignInButton(text = "Sign in with google") {
            onGoogleSignInClick()
        }


    }

}