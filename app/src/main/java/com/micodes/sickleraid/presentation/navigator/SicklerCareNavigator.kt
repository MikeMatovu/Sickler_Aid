package com.micodes.sickleraid.presentation.navigator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material.icons.outlined.Outbound
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.micodes.sickleraid.presentation.navigator.components.BottomNavigationItem
import com.micodes.sickleraid.presentation.navigator.components.SicklerCareBottomNavigation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SicklerCareNavigator(mainNavController: NavHostController) {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = Icons.Rounded.Home, text = "Home"),
            BottomNavigationItem(icon = Icons.Outlined.Outbound, text = "Symptoms"),
            BottomNavigationItem(icon = Icons.Outlined.AutoGraph, text = "Insights"),
            BottomNavigationItem(icon = Icons.Outlined.MedicalServices, text = "MedicineTable"),
        )
    }
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            SicklerCareBottomNavigation(
                items = bottomNavigationItems,
                navController = navController,
            )

        }) {
        val bottomPadding = it.calculateBottomPadding()
        BottomNavHost(
            mainNavController = mainNavController,
            navController = navController,
            bottomPaddingValues = bottomPadding,
            modifier = Modifier.padding(bottomPadding)
        )


    }
}




