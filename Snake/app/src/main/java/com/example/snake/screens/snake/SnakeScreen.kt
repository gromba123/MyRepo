package com.example.snake.screens.snake

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

private const val SNAKE_RADIUS = 5.0F
private val SNAKE_COLOR = Color.Green

@Composable
fun SnakeScreen() {
    val viewModel = hiltViewModel<SnakeViewModel>()
    val location = viewModel.location.observeAsState().value!!
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        drawCircle(
            color = SNAKE_COLOR,
            radius = SNAKE_RADIUS,
            center = Offset(location.x, location.y)
        )
    }
}