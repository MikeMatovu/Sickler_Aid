package com.micodes.sickleraid.presentation.navigator

import HealthInsightsScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material.icons.outlined.Outbound
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.micodes.sickleraid.presentation.doctor_detail.DoctorDetailsScreen
import com.micodes.sickleraid.presentation.home.HomeScreen
import com.micodes.sickleraid.presentation.navgraph.Screen
import com.micodes.sickleraid.presentation.navigator.components.BottomNavigationItem
import com.micodes.sickleraid.presentation.navigator.components.SicklerCareBottomNavigation
import com.micodes.sickleraid.presentation.profile.ProfileScreen
import com.micodes.sickleraid.presentation.symptoms.SymptomsScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SicklerCareNavigator() {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = Icons.Rounded.Home, text = "Home"),
            BottomNavigationItem(icon = Icons.Outlined.Outbound, text = "Search"),
            BottomNavigationItem(icon = Icons.Outlined.AutoGraph, text = "Symptoms"),
            BottomNavigationItem(icon = Icons.Outlined.AutoGraph, text = "Insights"),
            BottomNavigationItem(icon = Icons.Outlined.VideoLibrary, text = "Video"),
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    selectedItem = when (backStackState?.destination?.route) {
        Screen.HomeScreen.route -> 0
        Screen.Symptoms.route -> 1
        Screen.Videos.route -> 2
        Screen.Insights.route -> 3
        Screen.News.route -> 4
        else -> 0
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            SicklerCareBottomNavigation(
                items = bottomNavigationItems,
                selectedItem = selectedItem,
                onItemClick = { index ->
                    when (index) {
                        0 -> navigateToTab(
                            navController = navController,
                            route = Screen.HomeScreen.route
                        )

                        1 -> navigateToTab(
                            navController = navController,
                            route = Screen.Symptoms.route
                        )

                        2 -> navigateToTab(
                            navController = navController,
                            route = Screen.Videos.route
                        )

                        3 -> navigateToTab(
                            navController = navController,
                            route = Screen.Insights.route
                        )

                        4 -> navigateToTab(
                            navController = navController,
                            route = Screen.News.route
                        )
                    }
                }
            )

        }) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Screen.HomeScreen.route) {
                HomeScreen(navController = navController)
            }
            composable(route = Screen.Symptoms.route) {
                SymptomsScreen()
            }
            composable(route = Screen.Videos.route) {
                ProfileScreen()
            }
            composable(route = Screen.Insights.route) {
                HealthInsightsScreen()
            }
            composable(route = Screen.News.route) {
                DoctorDetailsScreen()
            }

        }

    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen_route ->
            popUpTo(screen_route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

