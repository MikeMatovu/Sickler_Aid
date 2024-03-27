package com.micodes.sickleraid.presentation.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Label
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun StatisticsScreen() {

    Column() {

        Row() {

            Text("Health Insights")
            Icon(imageVector = Icons.Default.Label, contentDescription = null)

        }

        Text("Last 7")

        Card() {
            Text("Health")
            Text("44%")
            Column() {
                Text("knjk")
            }
        }

        Card() {
            //Graph and the rest
        }

        Card() {
            Text("Top 3 recommendation")
            Row() {
                Column() {

                    Icon(imageVector = Icons.Default.Label, contentDescription = null)
                    Text("Reading")

                }
                Column() {

                    Icon(imageVector = Icons.Default.Label, contentDescription = null)
                    Text("Medication")

                }
                Column() {

                    Icon(imageVector = Icons.Default.Label, contentDescription = null)
                    Text("Journaling")
                    Text("You arent the way to pass all")

                }
            }
        }
    }
}