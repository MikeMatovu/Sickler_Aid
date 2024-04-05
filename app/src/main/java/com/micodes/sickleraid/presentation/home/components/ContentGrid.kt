package com.micodes.sickleraid.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.micodes.sickleraid.R
import com.micodes.sickleraid.presentation.home.SupportResource


@Composable
fun ContentGrid(resources: List<SupportResource>, height: Int) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 116.dp),
        modifier = Modifier
            .height(height.dp)
            .padding(8.dp)
    ) {
        items(resources) { resource ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .size(128.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = resource.image,
                        contentDescription = resource.title,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = resource.title)
                }


            }
        }
    }
}



@Composable
fun ContentGrid2(resources: List<SupportResource>) {
    val columns = 3 // Define the number of columns in your grid
    val rows = resources.size / columns + if (resources.size % columns > 0) 1 else 0

    Column {
        for (row in 0 until rows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (column in 0 until columns) {
                    val index = row * columns + column
                    if (index < resources.size) {
                        val resource = resources[index]
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(128.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = resource.image,
                                    contentDescription = resource.title,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = resource.title)
                            }
                        }
                    } else {
                        // If there are not enough items to fill the last row, you can either leave it empty or add placeholders
                        Box(modifier = Modifier.size(128.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewContentGrid() {
    val dummyResources = listOf(
        SupportResource(title = "Resource 1", image = painterResource(id = R.drawable.cells)),
        SupportResource(title = "Resource 1", image = painterResource(id = R.drawable.cells)),
        SupportResource(title = "Resource 1", image = painterResource(id = R.drawable.cells)),
        SupportResource(title = "Resource 1", image = painterResource(id = R.drawable.cells)),
        SupportResource(title = "Resource 1", image = painterResource(id = R.drawable.cells)),
        SupportResource(title = "Resource 1", image = painterResource(id = R.drawable.cells)),

        )
    ContentGrid(resources = dummyResources, height = 200)
}
