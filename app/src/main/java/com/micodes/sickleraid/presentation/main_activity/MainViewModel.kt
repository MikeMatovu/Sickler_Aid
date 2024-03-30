package com.micodes.sickleraid.presentation.main_activity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micodes.sickleraid.domain.services.AccountService
import com.micodes.sickleraid.presentation.navgraph.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val accountService: AccountService,
): ViewModel() {

    private val _startDestination = mutableStateOf(Screen.OnBoardingNavigation.route)
    val startDestination: State<String> = _startDestination


    init {
        checkUserSignInState()
    }
    private fun checkUserSignInState() {
        viewModelScope.launch {
            val isSignedIn = accountService.hasUser
            _startDestination.value = if (isSignedIn) Screen.AppNavigation.route else Screen.OnBoardingNavigation.route
        }
    }
}