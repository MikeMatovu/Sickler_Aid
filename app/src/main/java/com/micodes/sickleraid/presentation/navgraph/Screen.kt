package com.micodes.sickleraid.presentation.navgraph

sealed class Screen(val route:String){
    object SetUpScreen: Screen("SetUpScreen")
    object HomeScreen:Screen("HomeScreen")
    object LoginScreen:Screen("login")
    object SignUpScreen:Screen("signup")
}