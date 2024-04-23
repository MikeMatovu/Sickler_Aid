package com.micodes.sickleraid.presentation.navigator

import HealthInsightsScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.micodes.sickleraid.presentation.daily_checkup.DailyCheckupScreen
import com.micodes.sickleraid.presentation.doctor_detail.DoctorDetailsScreen
import com.micodes.sickleraid.presentation.doctor_detail.DoctorListScreen
import com.micodes.sickleraid.presentation.home.HomeScreen
import com.micodes.sickleraid.presentation.medical_records.MedicalRecordsScreen
import com.micodes.sickleraid.presentation.medicine.MedicineScreen
import com.micodes.sickleraid.presentation.navgraph.Screen
import com.micodes.sickleraid.presentation.profile.ProfileScreen
import com.micodes.sickleraid.presentation.symptoms.SymptomsScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun BottomNavHost(
    mainNavController: NavHostController,
    navController: NavHostController,
    bottomPaddingValues: Dp,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
        modifier = Modifier.padding(bottom = bottomPaddingValues)
    ) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.Symptoms.route) {
            SymptomsScreen()
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController, mainNavController = mainNavController)
        }
        composable(route = Screen.Insights.route) {
            HealthInsightsScreen(navController = navController)
        }
        composable(route = Screen.Medicine.route) {
            MedicineScreen(navController = navController)
        }
        composable(route = Screen.News.route) {
            //TODO: Replace with actual implementation
            MedicineScreen(navController = navController)
        }
        composable(route = Screen.DailyCheckup.route) {
            DailyCheckupScreen(navController = navController)
        }
        composable(route = Screen.MedicalRecords.route) {
            MedicalRecordsScreen(navController = navController)
        }

        composable(route = Screen.DoctorList.route) {
            DoctorListScreen(navController = navController)
        }
        composable(route = "${Screen.Doctor.route}/{doctorId}") { backStackEntry ->
            val arguments = backStackEntry.arguments
            val doctorId = arguments?.getString("doctorId")
            DoctorDetailsScreen(doctorId = doctorId, navController = navController)
        }
    }
}