package com.example.snake.utils

import com.example.snake.screens.snake.SNAKE_RADIUS

private val STARTING_LOCATION = Location(50F, 50F)

data class Location(
    val x: Float,
    val y: Float
)

fun Location.addDirection(direction: Direction): Location {
    return Location(x + direction.location.x, y + direction.location.y)
}

enum class Direction(
    val location: Location
) {
    UP(Location(0F, -SNAKE_RADIUS)),
    DOWN(Location(0F, SNAKE_RADIUS)),
    LEFT(Location(-SNAKE_RADIUS, 0F)),
    RIGHT(Location(SNAKE_RADIUS, 0F)),
}

fun Direction.getOpposite() =
    if (this == Direction.UP) Direction.DOWN
    else if (this == Direction.DOWN) Direction.UP
    else if (this == Direction.RIGHT) Direction.LEFT
    else Direction.RIGHT

fun Direction.isOpposite(direction: Direction): Boolean =
    if (this == Direction.UP) {
        direction == Direction.DOWN
    } else if (this == Direction.DOWN) {
        direction == Direction.UP
    } else if (this == Direction.RIGHT) {
        direction == Direction.LEFT
    } else {
        direction == Direction.RIGHT
    }

data class SnakePart(
    val location: Location,
    val direction: Direction
)

data class Apple(
    val location: Location
)

data class DirectionChange(
    val direction: Direction,
    val location: Location,
    var actualIndex: Int = 0
)

data class GameElements(
    val snakeParts: List<SnakePart> = listOf(SnakePart(STARTING_LOCATION, Direction.RIGHT)),
    val appleList: List<Apple> = listOf(),
    val locationsList: List<DirectionChange> = listOf()
)

data class MovementUpdates(
    val snakeParts: List<SnakePart>,
    val appleList: List<Apple>
)

operator fun <T> List<T>.plus(t: T): List<T> {
    val list = this.toMutableList()
    list.add(t)
    return list
}