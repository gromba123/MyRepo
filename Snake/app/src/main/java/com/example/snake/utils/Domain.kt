package com.example.snake.utils

data class Location(
    val x: Float,
    val y: Float
)

fun Location.addDirection(location: Location): Location {
    return Location(x + location.x, y + location.y)
}

enum class Direction(
    val location: Location
) {
    UP(Location(0F, -20F)),
    DOWN(Location(0F, 20F)),
    LEFT(Location(-20F, 0F)),
    RIGHT(Location(20F, 0F)),
}

data class SnakePart(
    val location: Location
)