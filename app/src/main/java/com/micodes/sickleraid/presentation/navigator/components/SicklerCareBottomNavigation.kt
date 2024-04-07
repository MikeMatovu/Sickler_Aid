package com.micodes.sickleraid.presentation.navigator.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.micodes.sickleraid.presentation.navgraph.Screen

@Composable
fun SicklerCareBottomNavigation(
    modifier: Modifier = Modifier,
    items: List<BottomNavigationItem>,
    navController: NavHostController,
) {

    fun onItemClick(index: Int) {
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
                route = Screen.Insights.route
            )

            3 -> navigateToTab(
                navController = navController,
                route = Screen.News.route
            )
        }
    }

    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    selectedItem = when (backStackState?.destination?.route) {
        Screen.HomeScreen.route -> 0
        Screen.Symptoms.route -> 1
        Screen.Insights.route -> 2
        Screen.News.route -> 3
        else -> 0
    }
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 10.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selectedItem,
                onClick = {
                    selectedItem = index
                    onItemClick(index)

                },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null,
                            modifier = Modifier.size(28.dp)
                        )

                    }
                },
                label = {
                    Text(
                        text = item.text,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                    unselectedTextColor = MaterialTheme.colorScheme.onBackground,
                    indicatorColor = MaterialTheme.colorScheme.background
                ),
            )
        }
    }
}

fun navigateToTab(navController: NavController, route: String) {
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


data class BottomNavigationItem(
    val icon: ImageVector,
    val text: String
)