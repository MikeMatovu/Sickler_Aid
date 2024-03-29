package com.micodes.sickleraid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.micodes.sickleraid.presentation.auth.login.LoginScreen
import com.micodes.sickleraid.presentation.main_activity.SicklerAidApp
import com.micodes.sickleraid.presentation.navgraph.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           SicklerAidApp()


        }
    }
}




