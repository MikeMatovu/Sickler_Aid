package com.micodes.sickleraid.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SectionTitle(
    sectionTitle: String,
    buttonTitle: String,
    onButtonClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = sectionTitle,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.weight(1f))
        TextButton(onClick = onButtonClick) {
            Text(text = buttonTitle, style = MaterialTheme.typography.bodyMedium)
        }
    }
}


@Composable
@Preview(showBackground = true)
fun SectionTitlePreview() {
    SectionTitle(
        sectionTitle = "Medicines",
        buttonTitle = "View All"
    ) {}
}