package com.avikus.assignment.android

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.avikus.assignment.android.ui.theme.Purple80
import com.avikus.assignment.android.ui.theme.PurpleGrey40
import com.avikus.assignment.android.ui.theme.CompassBackground
import kotlin.math.roundToInt
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MainScreen(modifier: Modifier = Modifier, viewModel: MainViewModel = viewModel()) {
    val boatStatus by viewModel.boatStatus.collectAsStateWithLifecycle()
    var selectedUnit by remember { mutableStateOf("knots") }

    Column(
        modifier = modifier
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        boatStatus?.let { status ->
            if (!status.location.latitude.isNaN() && !status.location.longitude.isNaN()) {
                Text("위도: ${status.location.latitude}")
                Text("경도: ${status.location.longitude}")
            } else {
                Text("위치 정보 없음")
            }

            if (!status.speed.isNaN()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("속도: ${convertSpeed(status.speed, selectedUnit)}")
                    SpeedDropdownMenu(
                        selected = selectedUnit,
                        onUnitSelected = { selectedUnit = it }
                    )
                }
            } else {
                Text("속도 정보 없음")
            }

            if (!status.heading.isNaN()) {
                Text("Heading: ${status.heading}°")
                Compass(heading = status.heading)
            } else {
                Text("방향 정보 없음")
            }
        } ?: run {
            Text("Loading boat status...")
        }
    }
}

@Composable
fun SpeedDropdownMenu(
    selected: String,
    onUnitSelected: (String) -> Unit
) {
    var expandStatus by remember { mutableStateOf(false) }
    val options = listOf("knots", "mph", "kmp")

    Box(
        modifier = Modifier
            .clickable { expandStatus = true }
            .background(Purple80)
            .padding(horizontal = 4.dp)
    ) {
        Text(
            text = selected,
        )

        DropdownMenu(
            expanded = expandStatus,
            onDismissRequest = { expandStatus = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onUnitSelected(option)
                        expandStatus = false
                    }
                )
            }
        }
    }
}

@Composable
fun Compass(heading: Float, modifier: Modifier = Modifier) {
    val targetHeading by animateFloatAsState(
        targetValue = heading,
    )
    Column(modifier = Modifier
        .padding(vertical = 5.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "N",
            color = Color.Black,
            fontSize = 20.sp
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = "W",
                color = Color.Black,
                fontSize = 20.sp
            )
            Box(
                modifier = modifier
                    .padding(16.dp)
                    .size(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val radius = size.width / 2
                    val center = Offset(size.width / 2, size.height / 2)

                    drawCircle(
                        color = CompassBackground,
                        center = center,
                        radius = radius,
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.direction),
                    contentDescription = "Compass Arrow",
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            rotationZ = targetHeading
                        }
                )
                Text(
                    text = "${(heading).roundToInt()}°",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
            Text(
                text = "E",
                color = Color.Black,
                fontSize = 20.sp
            )
        }
        Text(
            text = "S",
            color = Color.Black,
            fontSize = 20.sp
        )
    }
}

fun convertSpeed(speed: Float, unit: String): String {
    if (speed.isNaN()) return "N/A"
    return when (unit) {
        "knots" -> (speed * 1.94384f).toString()
        "mph" -> (speed * 2.23694f).toString()
        "kmp" -> (speed * 3.6f).toString()
        else -> speed.toString()
    }
}
