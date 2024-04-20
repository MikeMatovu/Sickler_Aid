package com.micodes.sickleraid.presentation.onboarding

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.micodes.sickleraid.R
import com.micodes.sickleraid.data.remote.AuthState
import com.micodes.sickleraid.data.remote.DataProvider
import com.micodes.sickleraid.domain.model.Response
import com.micodes.sickleraid.presentation.auth.AuthViewModel
import com.micodes.sickleraid.presentation.auth.signup.AuthLoginProgressIndicator
import com.micodes.sickleraid.presentation.common.composable.BasicButton
import com.micodes.sickleraid.presentation.home.HomeScreen
import com.micodes.sickleraid.presentation.navgraph.Screen


@Composable
fun GetStarted(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val currentUser = viewModel.currentUser.collectAsState().value
    DataProvider.updateAuthState(currentUser)

    Log.i("AuthRepo", "Authenticated: ${DataProvider.isAuthenticated}")
    Log.i("AuthRepo", "Anonymous: ${DataProvider.isAnonymous}")
    Log.i("AuthRepo", "User: ${DataProvider.user}")

        Column(modifier = modifier) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f),
                painter = painterResource(id = R.drawable.onboard_image),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Welcome",
                modifier = Modifier.padding(30.dp),
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Your Health Matters",
                modifier = Modifier.padding(30.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            BasicButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                text = "GET STARTED",
                action = {
//                    viewModel.signInAnonymously()
                    navController.navigate(Screen.UserOnBoardingScreen.route)
                }
            )
        }

    //At the end split this into its own composable

    //HERE
    when (val anonymousResponse = DataProvider.anonymousSignInResponse) {
        is Response.Loading -> {
            Log.i("Login:AnonymousSignIn", "Loading")
            AuthLoginProgressIndicator()
        }
        is Response.Success -> anonymousResponse.data?.let { authResult ->
//            Log.i("Login:AnonymousSignIn", "Success: $authResult")
            navController.navigate(Screen.UserOnBoardingScreen.route)
        }
        is Response.Failure -> {
            Log.e("Login:AnonymousSignIn", "${anonymousResponse.e}")
        }
    }
    //HERE
}