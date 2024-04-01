package com.micodes.sickleraid.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.micodes.sickleraid.presentation.common.composable.BasicButton
import com.micodes.sickleraid.presentation.common.composable.BasicField
import com.micodes.sickleraid.presentation.common.composable.ProfileAvatar
import com.micodes.sickleraid.presentation.common.ext.basicButton
import com.micodes.sickleraid.presentation.profile.composable.Header
import com.micodes.sickleraid.presentation.profile.composable.SpaceVertical24
import com.micodes.sickleraid.presentation.profile.composable.SpaceVertical32
import com.micodes.sickleraid.presentation.profile.composable.TextButton

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    ProfileContent(
        uiState = state,
        onChangeFirstName = viewModel::onChangeFirstName,
        onChangeLastName = viewModel::onChangeLastName,
        onChangeEmail = viewModel::onChangeEmail,
        onChangePhone = viewModel::onChangePhone,
        onSaveUserInfo = viewModel::onSaveUserInfo
    )
}

@Composable
private fun ProfileContent(
    uiState: ProfileUiState,
    onChangeFirstName: (String) -> Unit,
    onChangeLastName: (String) -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangePhone: (String) -> Unit,
    onSaveUserInfo: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(
            title = "Account",
            subtitle = "Edit your profile information",
        )
        SpaceVertical32()

        ProfileAvatar(
            painter = rememberAsyncImagePainter(model = uiState.profilePictureLink),

            size = 128
        )
        SpaceVertical24()

        TextButton(text = "Change profile Picture") {}
        SpaceVertical32()

        BasicField(
            modifier = Modifier.padding(8.dp),
            text = "First Name",
            value = uiState.firstName,
            onNewValue = onChangeFirstName
        )
        BasicField(
            modifier = Modifier.padding(8.dp),
            text = "Last Name",
            value = uiState.lastName,
            onNewValue = onChangeLastName
        )
        BasicField(
            modifier = Modifier.padding(8.dp),
            text = "Email",
            value = uiState.email,
            onNewValue = onChangeEmail
        )
        BasicField(
            modifier = Modifier.padding(8.dp),
            text = "Phone Number",
            value = uiState.phone,
            onNewValue = onChangePhone
        )

        Spacer(modifier = Modifier.weight(1f))
        BasicButton(
            "UPDATE PROFILE",
            Modifier.basicButton()
        ) { onSaveUserInfo() }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}

