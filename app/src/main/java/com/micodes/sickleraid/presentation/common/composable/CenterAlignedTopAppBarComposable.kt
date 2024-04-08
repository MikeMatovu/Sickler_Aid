package com.micodes.sickleraid.presentation.common.composable

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material3.TextButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterAlignedTopAppBarComposable(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    profileImage: ImageVector,
    onBackPressed: () -> Unit,
    buttonText: String,
    onButtonClick: () -> Unit
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
//            Box(
//                modifier = Modifier
//                    .padding(end = 8.dp) // Adjust padding as needed
//                    .clickable { }
//                    .size(40.dp) // Adjust size as needed
//                    .clip(CircleShape)
//                    .border(
//                        width = 2.dp,
//                        color = Color.White,
//                        shape = CircleShape
//                    )
//            ) {
//                Image(
//                    imageVector = profileImage, // Pass your profile image Painter here
//                    contentDescription = "Profile",
//                    modifier = Modifier.fillMaxSize()
//                )
//            }
//            TextButton(
//                text = buttonText,
//                onClick = onButtonClick,
//              )
            TextButton(onClick = onButtonClick) {
                Text(text = buttonText)
            }
        },
        scrollBehavior = scrollBehavior,
    )
}