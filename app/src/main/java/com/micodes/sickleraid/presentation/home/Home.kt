package com.micodes.sickleraid.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.LineAxis
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.LineAxis
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavController,
    state: HomeState,
    onEvent: (Lifecycle.Event) -> Unit
) {

    var initState by remember {
        mutableStateOf(false)
    }

    val tabItems = listOf(
        TabItems(
            title = "Pie",
            unselectedIcon = Icons.Outlined.PieChart,
            selectedIcon = Icons.Filled.PieChart
        ),
        TabItems(
            title = "Bar",
            unselectedIcon = Icons.Outlined.BarChart,
            selectedIcon = Icons.Filled.BarChart
        ),
        TabItems(
            title = "Line",
            unselectedIcon = Icons.Outlined.LineAxis,
            selectedIcon = Icons.Filled.LineAxis
        ),
        TabItems(
            title = "Line",
            unselectedIcon = Icons.Outlined.LineAxis,
            selectedIcon = Icons.Filled.LineAxis
        ),
        TabItems(
            title = "Line",
            unselectedIcon = Icons.Outlined.LineAxis,
            selectedIcon = Icons.Filled.LineAxis
        )
    )

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    var pagerState = rememberPagerState {
        tabItems.size
    }

    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(pagerState) {

        if (!(pagerState.isScrollInProgress)) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    //Swipable screen
    Surface(modifier = Modifier.fillMaxSize()) {


        Scaffold(
            bottomBar = {
                BottomAppBar(
                ) {

                }
            }

        ) {
            Column(modifier = Modifier
                .height(300.dp)
                .padding(it)) {

                TabRow(selectedTabIndex = selectedTabIndex) {
                    tabItems.forEachIndexed { index, item ->

                        Tab(
                            selected = index == selectedTabIndex,
                            onClick = {
                                selectedTabIndex = index
                            },
                            text = {
                                Text(text = item.title)
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedTabIndex) {
                                        item.selectedIcon
                                    } else item.unselectedIcon, contentDescription = item.title
                                )
                            }
                        )

                    }
                }

                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false
                ) { index ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        when (index) {
                            0 -> {
//                            HomeScreen()
                            }

                            1 -> {

                            }

                            2 -> {

                            }

                            3 -> {

                            }

                            else -> {

                            }
                        }
                    }
                }
            }
        }
    }
}