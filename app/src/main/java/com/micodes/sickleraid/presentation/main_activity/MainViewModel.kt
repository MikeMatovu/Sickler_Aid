package com.micodes.sickleraid.presentation.main_activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micodes.sickleraid.domain.services.AccountService
import com.micodes.sickleraid.presentation.navgraph.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val accountService: AccountService,
    private val notificationBuilder: NotificationCompat.Builder,
    private val notificationManager: NotificationManagerCompat
) : ViewModel() {

    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    private val _startDestination = mutableStateOf(Screen.OnBoardingNavigation.route)
    val startDestination: State<String> = _startDestination


    init {
        checkUserSignInState()
    }

    private fun checkUserSignInState() {
        viewModelScope.launch {
            val isSignedIn = accountService.hasUser
            _startDestination.value =
                if (isSignedIn) Screen.AppNavigation.route else Screen.OnBoardingNavigation.route
            delay(300)
            _splashCondition.value = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun showSimpleNotification(context: Context, title: String, message: String) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission to post notifications
            ActivityCompat.requestPermissions(
                context as Activity, // Make sure context is an Activity
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                PERMISSION_REQUEST_CODE
            )
            return
        }
        // If permission is already granted, show the notification
        notificationManager.notify(
            1, notificationBuilder.setContentTitle(title)
                .setContentText(message)
                .build()
        )
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun updateSimpleNotification(context: Context) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission to post notifications
            ActivityCompat.requestPermissions(
                context as Activity, // Make sure context is an Activity
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                PERMISSION_REQUEST_CODE
            )
            return
        }
        notificationManager.notify(
            1, notificationBuilder
                .setContentTitle("NEW TITLE")
                .build()
        )
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun cancelSimpleNotification(context: Context) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity, // Make sure context is an Activity
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                PERMISSION_REQUEST_CODE
            )
            return
        }
        notificationManager.cancel(1)
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }


}