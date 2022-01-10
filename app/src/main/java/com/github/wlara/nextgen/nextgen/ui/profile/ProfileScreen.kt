package com.github.wlara.nextgen.nextgen.ui.profile

import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.wlara.nextgen.nextgen.ui.theme.NextGenTheme

@Composable
fun ProfileScreen(navigateUp: () -> Unit, profileViewModel: ProfileViewModel = hiltViewModel()) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        bitmap = it
    }
    NextGenTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .fillMaxHeight()

        ) {
            Button(onClick = navigateUp) {
                Text("Profile - Click me to go back!")
            }
            Button(onClick = { launcher.launch() }) {
                Text("Profile - Take Pic")
            }
            Button(onClick = {profileViewModel.getUsers() }) {
                Text("Test")
            }
            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    modifier = Modifier.fillMaxWidth(),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(name = "Profile Screen")
@Preview(name = "Profile Screen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewHomeScreen() {
    ProfileScreen({})
}