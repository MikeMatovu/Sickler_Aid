package com.micodes.sickleraid.presentation.health_insights.components

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun PrintReportBtn(onUriReceived: (Uri) -> Unit) {
    val context = LocalContext.current
    val mediaStorePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                onUriReceived(uri)
            }
        } else {
            // Handle the case where the user denies the permission request
        }
    }

    Button(onClick = {
        val createDocumentIntent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
            putExtra(Intent.EXTRA_TITLE, "report.pdf")
        }
        mediaStorePermissionLauncher.launch(createDocumentIntent)
    }) {
        Text("Generate Report")
    }
}