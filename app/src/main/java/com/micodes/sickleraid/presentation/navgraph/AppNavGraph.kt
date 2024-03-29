package com.micodes.sickleraid.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.micodes.sickleraid.presentation.navigator.SicklerCareNavigator

@Composable
fun AppNavGraph(
    startDestination: String,
    modifier: Modifier
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {

        composable(route = Screen.HomeScreen.route) {
            SicklerCareNavigator()
        }
    }
}