package com.micodes.sickleraid.presentation.main_activity

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.micodes.sickleraid.presentation.common.composable.AppDrawerContent
import com.micodes.sickleraid.presentation.common.snackbar.SnackbarManager
import com.micodes.sickleraid.presentation.navgraph.AppNavGraph
import com.micodes.sickleraid.ui.theme.SicklerAidTheme
import kotlinx.coroutines.CoroutineScope


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SicklerAidApp() {
    val viewModel: MainViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    SicklerAidTheme {

        Surface(color = MaterialTheme.colorScheme.background) {
            val appState = rememberAppState()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    AppDrawerContent(drawerState = drawerState)
                },
            ) {
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
                    AppNavGraph(
                        startDestination = viewModel.startDestination.value,
                        drawerState = drawerState,
                        modifier = Modifier.padding(innerPaddingModifier)
                    )
                }
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

