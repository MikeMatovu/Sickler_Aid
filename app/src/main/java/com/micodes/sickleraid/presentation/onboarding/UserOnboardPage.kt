package com.micodes.sickleraid.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.micodes.sickleraid.R
import com.micodes.sickleraid.presentation.common.composable.BasicButton
import com.micodes.sickleraid.presentation.navgraph.Screen
import com.micodes.sickleraid.presentation.navigator.SicklerCareNavigator

@Composable
fun UserOnBoardPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_app), //TODO: add image
                contentDescription = "Logo",
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Light") //TODO Add switch for light and dark
        }

        Spacer(modifier = Modifier.height(200.dp))
        Text(
            text = "Welcome to My sickle care",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineLarge

        )


        Spacer(modifier = Modifier.weight(1f))
        Column (
            verticalArrangement = Arrangement.Center
        ){
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Sign in or register to continue",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(100.dp))
            BasicButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                text = "LOG IN",
                action = {navController.navigate(Screen.LoginScreen.route)}
            )

            BasicButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                text = "REGISTER",
                action = {navController.navigate(Screen.SignUpScreen.route)}
            )
        }

    }
}
