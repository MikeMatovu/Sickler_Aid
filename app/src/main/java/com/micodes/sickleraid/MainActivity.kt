package com.micodes.sickleraid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.micodes.sickleraid.presentation.auth.login.LoginScreen
import com.micodes.sickleraid.presentation.main_activity.MainViewModel
import com.micodes.sickleraid.presentation.main_activity.SicklerAidApp
import com.micodes.sickleraid.presentation.navgraph.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().apply {
            setKeepOnScreenCondition(condition = { viewModel.splashCondition.value })
        }
        setContent {
            val isSystemInDarkMode = isSystemInDarkTheme()
            val systemUiColor = rememberSystemUiController()
            SideEffect {
                systemUiColor.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = !isSystemInDarkMode
                )
            }
           SicklerAidApp()


        }
    }
}




