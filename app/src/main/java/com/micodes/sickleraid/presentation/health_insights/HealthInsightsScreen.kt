import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.micodes.sickleraid.R
import com.micodes.sickleraid.presentation.common.composable.TopAppBarComposable
import com.micodes.sickleraid.presentation.health_insights.components.BarChart
import com.micodes.sickleraid.presentation.health_insights.components.DropDownMenuComposable
import com.micodes.sickleraid.presentation.health_insights.components.HabitItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Preview(showBackground = true)
@Composable
fun HealthInsightsScreen() {
    Scaffold (
        topBar = {
            TopAppBarComposable(
                title = {
                    Text(text = "Health Insights", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                },
                actions = listOf {
                    IconButton(onClick = { /* TODO */ }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_app),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ){paddingValues ->
        var selectedPeriod by remember { mutableStateOf(HealthPeriod.WEEKLY) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .consumeWindowInsets(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(8.dp))
            DropDownMenuComposable(
                modifier = Modifier
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Weekly health overview",
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(8.dp)
                )
                LinearProgressIndicator(
                    progress = 0.67f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),

                    )
                Text(
                    text = "67% of health",
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    // Bar Chart (Sample data, replace with actual data)
                    BarChart()

                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            //Second bar chart for other stats
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    // Bar Chart (Sample data, replace with actual data)
                    BarChart()

                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Recommended Habits", fontWeight = FontWeight.Medium, fontSize = 16.sp)

            // Recommended Habits
            HabitItem("Stay hydrated")
            HabitItem("Get enough sleep")
            HabitItem("Exercise regularly")
        }
    }
}


enum class HealthPeriod(val displayName: String) {
    WEEKLY("Last 7 days"),
    MONTHLY("Last 30 days"),
    YEARLY("Last 365 days")
}