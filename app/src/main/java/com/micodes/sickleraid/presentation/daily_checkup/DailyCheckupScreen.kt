package com.micodes.sickleraid.presentation.daily_checkup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.micodes.sickleraid.presentation.common.composable.CenterAlignedTopAppBarComposable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyCheckupScreen(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBarComposable(
                title = "Daily checkup",
                scrollBehavior = scrollBehavior,
                profileImage = Icons.Default.Person,
                onBackPressed = { navController.navigateUp() } // Handle back navigation
            )
        }
    ) {
        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Daily Checkup")
        }
    }
}