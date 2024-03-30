package com.micodes.sickleraid.presentation.navgraph

sealed class Screen(val route:String){
    object GetStartedScreen: Screen("GetStartedScreen")
    object UserOnBoardingScreen: Screen("UserOnBoardingScreen")
    object SetUpScreen: Screen("SetUpScreen")
    object HomeScreen:Screen("HomeScreen")
    object LoginScreen:Screen("login")
    object SignUpScreen:Screen("signup")

    object Insights:Screen("statistics")

    object Symptoms:Screen("symptoms")

    object News:Screen("news")

    object Videos:Screen("videos")

    object AppNavigation:Screen("appnavigation")
    object OnBoardingNavigation:Screen("onboardingnavigation")



}