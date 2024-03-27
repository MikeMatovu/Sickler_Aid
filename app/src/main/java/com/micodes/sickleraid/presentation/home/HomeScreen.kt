package com.micodes.sickleraid.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.micodes.sickleraid.data.remote.AuthState
import com.micodes.sickleraid.data.remote.DataProvider
import com.micodes.sickleraid.presentation.common.composable.AppBottomBarComposable
import com.micodes.sickleraid.presentation.common.composable.TopAppBarComposable
import com.micodes.sickleraid.presentation.home.components.ContentListItem
import com.micodes.sickleraid.presentation.home.components.ContentRow
import com.micodes.sickleraid.presentation.home.components.SectionTitle
import com.micodes.sickleraid.presentation.login.LoginScreen
import com.micodes.sickleraid.presentation.login.LoginViewModel
import com.micodes.sickleraid.presentation.navgraph.Screen
import com.micodes.sickleraid.presentation.signup.SignUpViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: SignUpViewModel = hiltViewModel()
//    state: HomeState  TODO: Add Home state
) {

    val openLoginDialog = remember { mutableStateOf(false) }
    val authState = DataProvider.authState
    Scaffold(
        topBar = {
            TopAppBarComposable()
        },
        bottomBar = {
            AppBottomBarComposable()
        },
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            SectionTitle()
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                ContentRow(modifier = Modifier)
                Spacer(modifier = Modifier.height(16.dp))
                ContentRow(modifier = Modifier)
            }

            SectionTitle()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                ContentListItem()
                ContentListItem()
                ContentListItem()
            }

            SectionTitle()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                ContentListItem()
                ContentListItem()
                ContentListItem()
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                ContentListItem()
                ContentListItem()
                ContentListItem()
            }
            //DUmmy content to be replaced
            Button(onClick = {
                navController.navigate(Screen.SignUpScreen.route)
            }) {
                Text(text = "Sign Up")
            }

            if (authState == AuthState.SignedIn) {
                Text(
                    DataProvider.user?.displayName ?: "Name Placeholder",
                    fontWeight = FontWeight.Bold
                )
                Text(DataProvider.user?.email ?: "Email Placeholder")
            } else {
                Text(
                    "Sign-in to view data!"
                )
            }

            Button(
                onClick = {
                    if (authState != AuthState.SignedIn)
                        openLoginDialog.value = true
                    else
                        authViewModel.signOut()
                },
                modifier = Modifier
                    .size(width = 200.dp, height = 50.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                )
            ) {
                Text(
                    text = if (authState != AuthState.SignedIn) "Sign-in" else "Sign out",
                    modifier = Modifier.padding(6.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            //End dummy content
        }
        //MOre dummy content
        val testvm: LoginViewModel = hiltViewModel()
        AnimatedVisibility(visible = openLoginDialog.value) {
            Dialog(
                onDismissRequest = { openLoginDialog.value = false },
                properties = DialogProperties(
                    usePlatformDefaultWidth = false
                )
            ) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LoginScreen( openAndPopUp = { email, password ->
                        // Mock implementation for openAndPopUp
                        println("Email: $email, Password: $password")
                    }, testvm)
                }
            }
        }
        //End dummy content
    }

}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())

}


//Text(text = "Home screen")
//Button(onClick = {
//    navController.navigate(Screen.LoginScreen.route)
//}) {
//    Text(text = "Login")
//}
//Button(onClick = {
//    navController.navigate(Screen.SignUpScreen.route)
//}) {
//    Text(text = "Sign Up")
//}