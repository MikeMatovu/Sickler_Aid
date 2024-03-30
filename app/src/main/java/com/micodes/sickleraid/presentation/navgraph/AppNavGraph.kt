package com.micodes.sickleraid.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.micodes.sickleraid.presentation.auth.login.LoginScreen
import com.micodes.sickleraid.presentation.auth.signup.SignUpScreen
import com.micodes.sickleraid.presentation.main_activity.SicklerAidAppState
import com.micodes.sickleraid.presentation.main_activity.rememberAppState
import com.micodes.sickleraid.presentation.navigator.SicklerCareNavigator
import com.micodes.sickleraid.presentation.onboarding.GetStarted
import com.micodes.sickleraid.presentation.onboarding.UserOnBoardPage

@Composable
fun AppNavGraph(
    startDestination: String,
    modifier: Modifier
) {
    val appState = rememberAppState()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Screen.OnBoardingNavigation.route,
            startDestination = Screen.GetStartedScreen.route
        ) {
            composable(route = Screen.GetStartedScreen.route) {
                GetStarted(navController = navController)
            }
            composable(route = Screen.UserOnBoardingScreen.route) {
                UserOnBoardPage(navController = navController)
            }
            composable(Screen.LoginScreen.route) {
                LoginScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
            }

            composable(Screen.SignUpScreen.route) {
                SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
            }
        }

        navigation(
            route = Screen.AppNavigation.route,
            startDestination = Screen.HomeScreen.route
        ) {
            composable(route = Screen.HomeScreen.route) {
                SicklerCareNavigator()
            }
        }


    }
}