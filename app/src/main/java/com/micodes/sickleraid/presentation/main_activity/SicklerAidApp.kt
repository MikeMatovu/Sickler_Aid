package com.micodes.sickleraid.presentation.main_activity

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.micodes.sickleraid.SicklerAidTheme
import com.micodes.sickleraid.presentation.common.snackbar.SnackbarManager
import com.micodes.sickleraid.presentation.home.HomeScreen
import com.micodes.sickleraid.presentation.auth.login.LoginScreen
import com.micodes.sickleraid.presentation.navgraph.Screen
import com.micodes.sickleraid.presentation.auth.signup.SignUpScreen
import com.micodes.sickleraid.presentation.navgraph.AppNavGraph
import com.micodes.sickleraid.presentation.navigator.SicklerCareNavigator
import kotlinx.coroutines.CoroutineScope


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SicklerAidApp() {
    val snackbarHostState = remember { SnackbarHostState() }
    SicklerAidTheme {

        Surface(color = MaterialTheme.colorScheme.background) {
            val appState = rememberAppState()

            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(
                                snackbarData,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    )
                },
            ) { innerPaddingModifier ->

//                NavHost(
//                    navController = appState.navController,
//                    startDestination = Screen.HomeScreen.route,
//                    modifier = Modifier.padding(innerPaddingModifier)
//                ) {
//                    sicklerAidGraph(appState)
//                }
                AppNavGraph(Screen.HomeScreen.route , modifier = Modifier.padding(innerPaddingModifier))
            }
        }
    }
}


@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(navController, snackbarManager, coroutineScope) {
        SicklerAidAppState(navController, snackbarManager, coroutineScope)
    }


//Not being used currently
fun NavGraphBuilder.sicklerAidGraph(appState: SicklerAidAppState) {
    composable(Screen.HomeScreen.route) {
//        HomeScreen(navController = appState.navController)
        SicklerCareNavigator()
    }
    composable(Screen.LoginScreen.route) {
//        LoginScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(Screen.SignUpScreen.route) {
        SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }


}