package com.micodes.sickleraid.presentation.common.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.micodes.sickleraid.presentation.profile.composable.TextButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterAlignedTopAppBarComposable(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    profileImage: ImageVector,
    onBackPressed: () -> Unit,
) {

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        ),
        title = {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {

            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }

        },
        actions = {
            Box(
                modifier = Modifier
                    .padding(end = 8.dp) // Adjust padding as needed
                    .clickable { }
                    .size(40.dp) // Adjust size as needed
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
            ) {
                Image(
                    imageVector = profileImage, // Pass your profile image Painter here
                    contentDescription = "Profile",
                    modifier = Modifier.fillMaxSize()
                )
            }
            TextButton(text = "EDIT") {
                
            }
        },
        scrollBehavior = scrollBehavior,
    )
}