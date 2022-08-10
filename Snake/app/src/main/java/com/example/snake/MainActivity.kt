package com.example.snake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.snake.screens.MainScreen
import com.example.snake.screens.snake.SnakeScreen
import com.example.snake.ui.theme.SnakeTheme
import com.example.snake.utils.HOME
import com.example.snake.utils.SNAKE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnakeTheme {
                // A surface container using the 'background' color from the theme
                BaseLayout()
            }
        }
    }
}

@Composable
fun BaseLayout() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = HOME) {
            composable(route = HOME) {
                MainScreen(navController = navController)
            }
            composable(route = SNAKE) {
                SnakeScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SnakeTheme {
        BaseLayout()
    }
}