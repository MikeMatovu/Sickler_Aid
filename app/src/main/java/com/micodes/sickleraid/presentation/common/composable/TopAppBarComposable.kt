package com.micodes.sickleraid.presentation.common.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.Cast
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.micodes.sickleraid.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComposable(
    title: @Composable () -> Unit,
    actions: List<@Composable () -> Unit>
) {
    TopAppBar(
        title = title,
        actions = {
            actions.forEach { action ->
                action()
            }
        }
    )
}