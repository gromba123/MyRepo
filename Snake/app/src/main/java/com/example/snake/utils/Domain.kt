package com.example.snake.utils

data class Location(
    val x: Float,
    val y: Float
)

fun Location.addDirection(location: Location): Location {
    return Location(x + location.x, y + location.y)
}

data class SnakePart(
    val location: Location
)