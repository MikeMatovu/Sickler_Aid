package com.micodes.sickleraid.presentation.common.composable

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.micodes.sickleraid.data.remote.AuthState
import com.micodes.sickleraid.data.remote.DataProvider
import com.micodes.sickleraid.domain.model.Response
import com.micodes.sickleraid.presentation.auth.AuthViewModel
import com.micodes.sickleraid.presentation.auth.signup.AuthLoginProgressIndicator
import com.micodes.sickleraid.presentation.navgraph.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawerContent(
    drawerState: DrawerState,
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val authState = DataProvider.authState
    val scope = rememberCoroutineScope()
    ModalDrawerSheet {

        Spacer(modifier = Modifier.height(16.dp))

        NavigationDrawerItem(
            label = { Text(text = "Sign Out") },
            selected = false,
            onClick = {
                if (authState != AuthState.SignedIn)
                    Log.d("LOGGED", "User is out")
                else
                    authViewModel.signOut()
                scope.launch {
                    drawerState.close()
                }
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(text = "Edit profile") },
            selected = false,
            onClick = {
                navController.navigate(Screen.Symptoms.route)
                scope.launch {
                    drawerState.close()
                }
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(text = "Navigation Item") },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.close()
                }
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(text = "Navigation Item") },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.close()
                }
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

    }
}