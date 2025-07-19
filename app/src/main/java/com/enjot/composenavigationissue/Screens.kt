package com.enjot.composenavigationissue

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun HomeScreen(
    navigateToDetailScreen: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Home screen",
            style = MaterialTheme.typography.displayMedium
        )
        Button(
            onClick = navigateToDetailScreen,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Text("Navigate to Detail Screen")
        }
    }
}

@Composable
fun MapScreen(
    navigateToDetailScreen: () -> Unit
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition
            .fromLatLngZoom(LatLng(50.06, 19.93), 15f)
    }
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            cameraPositionState = cameraPositionState,
            modifier = Modifier.fillMaxSize()
        )
        Button(
            onClick = navigateToDetailScreen,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Text("Navigate to Detail Screen")
        }
    }
}

@Composable
fun DetailScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Detail Screen",
            style = MaterialTheme.typography.displayMedium
        )
    }
}