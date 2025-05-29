package com.enjot.composenavigationissue

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


@Composable
fun RowScope.NavBarItem(
    name: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = name
            )
        },
        label = { Text(name) },
        selected = isSelected,
        onClick = onClick
    )
}

class HomeViewModel : ViewModel() {

    private var _counter = MutableStateFlow(0)
    val counter = _counter.asStateFlow()

    fun counterInc() {
        _counter.value++
    }
}

@Composable
fun HomeScreen(
    navigateToDetailScreen: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val viewModelCounter by viewModel.counter.collectAsStateWithLifecycle()
    var rememberSaveableCounter by rememberSaveable { mutableIntStateOf(0) }
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text("ViewModel counter: $viewModelCounter")
        Text("rememberSaveable counter: $rememberSaveableCounter")
        Button(
            onClick = {
                viewModel.counterInc()
                rememberSaveableCounter++
            }
        ) {
            Text("Counter++")
        }
        Spacer(Modifier.height(64.dp))
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
        position = CameraPosition.fromLatLngZoom(LatLng(50.06, 19.93), 15f)
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
        Text("Detail Screen")
    }
}