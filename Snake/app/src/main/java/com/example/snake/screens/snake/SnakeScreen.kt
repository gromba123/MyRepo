package com.example.snake.screens.snake

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.snake.utils.Direction

private const val SNAKE_RADIUS = 20.0F
private val SNAKE_COLOR = Color.Green
private val BUTTONS_PADDING = 10.dp
private val BUTTONS_BETWEEN_PADDING = 20.dp

@Composable
fun SnakeScreen() {
    val viewModel = hiltViewModel<SnakeViewModel>()
    Box(
        contentAlignment = Alignment.BottomEnd
    ) {
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
        BuildDirectionButtons(viewModel = viewModel)
    }
}

@Composable
fun BuildDirectionButtons(
    viewModel: SnakeViewModel
) {
    Box(
        modifier = Modifier
            .padding(
                end = BUTTONS_PADDING,
                bottom = BUTTONS_PADDING
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(BUTTONS_BETWEEN_PADDING)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowUpward,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .clickable {
                        viewModel.changeDirection(Direction.UP)
                    }
            )
            Icon(
                imageVector = Icons.Filled.ArrowDownward,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .clickable {
                        viewModel.changeDirection(Direction.DOWN)
                    }
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(BUTTONS_BETWEEN_PADDING)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .clickable {
                        viewModel.changeDirection(Direction.LEFT)
                    }
            )
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .clickable {
                        viewModel.changeDirection(Direction.RIGHT)
                    }
            )
        }
    }
}