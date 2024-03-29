package com.micodes.sickleraid.presentation.symptoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.micodes.sickleraid.R
import com.micodes.sickleraid.presentation.common.composable.TopAppBarComposable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymptomsScreen() {
    Scaffold(
        topBar = {
            TopAppBarComposable(
                title = {
                    Text(text = "Symptoms Tracking")
                },
                actions = listOf {
                    IconButton(onClick = { /* TODO */ }) {
                        Image(
                            painter = painterResource(id = R.drawable.test_img),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {

        }
    }
}