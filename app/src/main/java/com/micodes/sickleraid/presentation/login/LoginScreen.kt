package com.micodes.sickleraid.presentation.login

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
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException
import com.micodes.sickleraid.data.remote.DataProvider
import com.micodes.sickleraid.domain.model.Response
import com.micodes.sickleraid.presentation.common.composable.BasicButton
import com.micodes.sickleraid.presentation.common.composable.BasicTextButton
import com.micodes.sickleraid.presentation.common.composable.BasicToolbar
import com.micodes.sickleraid.presentation.common.composable.EmailField
import com.micodes.sickleraid.presentation.common.composable.PasswordField
import com.micodes.sickleraid.presentation.common.ext.basicButton
import com.micodes.sickleraid.presentation.common.ext.fieldModifier
import com.micodes.sickleraid.presentation.common.ext.textButton
import com.micodes.sickleraid.presentation.signup.AuthLoginProgressIndicator
import com.micodes.sickleraid.presentation.signup.SignUpViewModel


@Composable
fun LoginScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
    //To remove

    loginState: MutableState<Boolean>? = null
) {
    val uiState by viewModel.uiState

    // To remove
    val authViewModel: SignUpViewModel = hiltViewModel()
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
        onSignInClick = { viewModel.onSignInClick(openAndPopUp) },
        onForgotPasswordClick = viewModel::onForgotPasswordClick
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
            loginState?.let { it.value = false }
        }
        is Response.Failure -> {
            Log.e("Login:GoogleSignIn", "${signInWithGoogleResponse.e}")
        }
    }
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
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
        val authViewModel: SignUpViewModel = hiltViewModel()
        Button(
            onClick = {
                // TODO: Sign in with Google
                authViewModel.oneTapSignIn()
            },
            modifier = Modifier
                .size(width = 300.dp, height = 50.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            )
        ) {
            Image(
                imageVector = Icons.Default.GppGood,
                contentDescription = ""
            )
            Text(
                text = "Sign in with Google",
                modifier = Modifier.padding(6.dp),
                color = Color.Black.copy(alpha = 0.5f)
            )
        }
    }
}