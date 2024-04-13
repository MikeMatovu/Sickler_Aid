package com.micodes.sickleraid.presentation.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.micodes.sickleraid.R
import com.micodes.sickleraid.presentation.home.MedicineResource
import com.micodes.sickleraid.presentation.home.SupportResource


@Composable
fun ResourcesRow(resources: List<SupportResource>, onResourceClick: (String) -> Unit) {
    LazyRow(
        modifier = Modifier
            .padding(8.dp)
    ) {
        items(resources) { resource ->
            ResourceItem(
                onResourceClick = { onResourceClick(resource.url) },
                title = resource.title,
                imageResourceId = resource.imageResourceId
            )
        }
    }
}

@Composable
fun MedicineRow(medicines: List<MedicineResource>, onMedicineClick: (String) -> Unit) {

    LazyRow(
        modifier = Modifier
            .padding(8.dp)
    ) {
        items(medicines) { medicine ->
            MedicineItem(
                name = medicine.name,
                onMedicineClick = { onMedicineClick(medicine.name) },
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewContentGrid() {
    val dummyResources = listOf(
        SupportResource(title = "Resource 1", url = "", imageResourceId = R.drawable.cells),
        SupportResource(title = "Resource 1", url = "", imageResourceId = R.drawable.cells),
        SupportResource(title = "Resource 1", url = "", imageResourceId = R.drawable.cells),
        SupportResource(title = "Resource 1", url = "", imageResourceId = R.drawable.cells),
        SupportResource(title = "Resource 1", url = "", imageResourceId = R.drawable.cells),
        SupportResource(title = "Resource 1", url = "", imageResourceId = R.drawable.cells),

        )
//    ResourcesRow(resources = dummyResources)
}
