import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.micodes.sickleraid.R
import com.micodes.sickleraid.presentation.charts.BarchartWithSolidBars
import com.micodes.sickleraid.presentation.common.composable.ProgressIndicatorComposable
import com.micodes.sickleraid.presentation.common.composable.TopAppBarComposable
import com.micodes.sickleraid.presentation.health_insights.HealthInsightsViewModel
import com.micodes.sickleraid.presentation.health_insights.components.DropDownMenuComposable
import com.micodes.sickleraid.presentation.health_insights.components.HabitItem
import com.micodes.sickleraid.presentation.health_insights.components.PrintReportBtn
import com.micodes.sickleraid.presentation.main_activity.MainViewModel
import com.micodes.sickleraid.presentation.navgraph.Screen
import java.io.InputStream
import java.io.OutputStream

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun HealthInsightsScreen(
    navController: NavController,
    viewModel: HealthInsightsViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    Scaffold(
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
    ) { paddingValues ->
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
                    progress = state.prediction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),

                    )
                if (state.prediction > 0.5) {
                    mainViewModel.showSimpleNotification(
                        context = context,
                        title = "High risk",
                        message = "You are at a high risk of getting a crisis today, consider taking the" +
                                "required steps and book appointment with doctor"
                    )
                }
                Text(
                    text = "${state.prediction * 100}% possibility of a crisis",
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Weekly temperature graph",
                modifier = Modifier
                    .padding(8.dp),
                style = MaterialTheme.typography.headlineMedium
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    if (state.isLoading) {
                        ProgressIndicatorComposable()
                    } else {
                        if (state.isEmpty) {
                            Text(
                                text = "No data available!, please input your records",
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        } else {
                            BarchartWithSolidBars(state.barData)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            //Second bar chart for other stats
            Text(
                text = "Weekly temperature graph",
                modifier = Modifier
                    .padding(8.dp),
                style = MaterialTheme.typography.headlineMedium
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    if (state.isLoading) {
                        ProgressIndicatorComposable()
                    } else {
                        if (state.isEmpty) {
                            Text(
                                text = "No data available! , please input your records",
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        } else {
                            BarchartWithSolidBars(state.barData)
                        }
                    }

                }


            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Recommended Habits", fontWeight = FontWeight.Medium, fontSize = 16.sp)

            // Recommended Habits
            HabitItem("Stay hydrated")
            HabitItem("Get enough sleep")
            HabitItem("Exercise regularly")


            //  Refresh test button
            Button(onClick = { viewModel.refreshData() }) {
                Text(text = "REFRESH")
            }

            //START DUMMY CONTENT HERE


            val pdfUri = state.pdfUri

            PrintReportBtn { uri ->
                viewModel.updatePdfUri(uri)
            }

            pdfUri?.let { uri ->
                LaunchedEffect(uri) {
                    val sourceUri = viewModel.generatePdfReport(context)
                    sourceUri?.let {
                        var outputStream: OutputStream? = null
                        var inputStream: InputStream? = null
                        try {
                            outputStream = context.contentResolver.openOutputStream(uri)
                            inputStream = context.contentResolver.openInputStream(it)
                            if (outputStream != null) {
                                inputStream?.copyTo(outputStream)
                            }
                        } catch (e: Exception) {
                            Log.e("PDF", "Exception when copying PDF", e)
                        } finally {
                            outputStream?.close()
                            inputStream?.close()
                        }
                    }
                }
            }

            Button(onClick = {
                navController.navigate(Screen.DoctorList.route)
            }) {
                Text("My Doctor")
            }
        }
    }
}


enum class HealthPeriod(val displayName: String) {
    WEEKLY("Last 7 days"),
    MONTHLY("Last 30 days"),
    YEARLY("Last 365 days")
}
