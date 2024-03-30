package com.micodes.sickleraid.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.micodes.sickleraid.presentation.profile.composable.DefaultButton
import com.micodes.sickleraid.presentation.profile.composable.Header
import com.micodes.sickleraid.presentation.profile.composable.ProfileAvatar
import com.micodes.sickleraid.presentation.profile.composable.SpaceHorizontal16
import com.micodes.sickleraid.presentation.profile.composable.SpaceVertical24
import com.micodes.sickleraid.presentation.profile.composable.SpaceVertical32
import com.micodes.sickleraid.presentation.profile.composable.TextButton
import com.micodes.sickleraid.presentation.profile.composable.InformationCard

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    ProfileContent(
        state = state,
        onChangeFirstName = viewModel::onChangeFirstName,
        onChangeLastName = viewModel::onChangeLastName,
        onChangeLocation = viewModel::onChangeLocation,
        onChangeEmail = viewModel::onChangeEmail,
        onChangePhone = viewModel::onChangePhone,
        onSaveUserInfo = viewModel::onSaveUserInfo
    )
}

@Composable
private fun ProfileContent(
    state: ProfileUiState,
    onChangeFirstName: (String) -> Unit,
    onChangeLastName: (String) -> Unit,
    onChangeLocation: (String) -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangePhone: (String) -> Unit,
    onSaveUserInfo: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(
            title = "Account",
            subtitle = "Edit your profile information",
        )
        SpaceVertical32()

        ProfileAvatar(
            painter = rememberAsyncImagePainter(model = state.profilePictureLink),

            size = 128
        )
        SpaceVertical24()

        TextButton(text = "Change profile Picture") {}
        SpaceVertical32()

        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(1F)) {
                InformationCard(
                    title = "First Name",
                    information = state.firstName,
                    onTextChange = onChangeFirstName
                )
            }
            SpaceHorizontal16()
            Box(modifier = Modifier.weight(1F)) {
                InformationCard(
                    title = "Last Name",
                    information = state.lastName,
                    onTextChange = onChangeLastName
                )
            }
        }
        InformationCard(
            title = "Location",
            information = state.location,
            onTextChange = onChangeLocation
        )
        InformationCard(
            title = "Email",
            information = state.email,
            onTextChange = onChangeEmail
        )
        InformationCard(
            title = "Phone Number",
            information = state.phone,
            onTextChange = onChangePhone
        )

        Spacer(modifier = Modifier.weight(1F))
        DefaultButton(buttonText = "Save", onClick = onSaveUserInfo)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}

