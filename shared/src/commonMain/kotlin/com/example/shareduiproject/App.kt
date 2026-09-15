package com.example.shareduiproject

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import com.example.shareduiproject.viewModel.LocationUiState
import com.example.shareduiproject.viewModel.LocationViewModel
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

import shareduiproject.shared.generated.resources.Res
import shareduiproject.shared.generated.resources.compose_multiplatform
import kotlin.time.Clock

@Composable
fun App(
    onPermissionRequest: () -> Unit = {},
    viewModel: LocationViewModel = koinInject()
) {
    val uiState by viewModel.uiState.collectAsState()

    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Today's date is ${todaysDate()}",
                modifier = Modifier.padding(20.dp),
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val isTracking by viewModel.isTracking.collectAsState()

            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        onPermissionRequest()
                        viewModel.fetchLocation()
                    }
                ) {
                    Text("Get Once")
                }

                if (!isTracking) {
                    Button(
                        onClick = {
                            onPermissionRequest()
                            viewModel.startBackgroundTracking()
                        }
                    ) {
                        Text("Start Background")
                    }
                } else {
                    androidx.compose.material3.Button(
                        onClick = { viewModel.stopBackgroundTracking() },
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Stop Background")
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = if (isTracking) "Status: 🟢 Background Tracking Active" else "Status: ⚪ Background Tracking Inactive",
                fontSize = 14.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            when (val state = uiState) {
                is LocationUiState.Idle -> {
                    Text("Location not fetched yet")
                }
                is LocationUiState.Loading -> {
                    Text("Fetching location...")
                }
                is LocationUiState.Success -> {
                    Text(
                        text = "Lat: ${state.coordinates.latitude}, Lon: ${state.coordinates.longitude}",
                        fontSize = 16.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
                is LocationUiState.Error -> {
                    Text(
                        text = "Error: ${state.message}",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}


fun todaysDate() : String {

    fun LocalDateTime.Format() = toString().substringBefore('T')

    val now = Clock.System.now();
    val zone = TimeZone.currentSystemDefault()

    return now.toLocalDateTime(zone).Format()
}