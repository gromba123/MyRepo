package com.example.snake.utils

import com.example.snake.screens.snake.SNAKE_RADIUS
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

fun generateApple(
    height: Float,
    width: Float,
    snakeParts: List<SnakePart>
): Apple {
    while (true) {
        val x = (0..width.toInt()).random().toFloat()
        val y = (0..height.toInt()).random().toFloat()
        val appleLocation = Location(x, y)
        if (snakeParts.none { computeDistance(it.location, appleLocation, SNAKE_RADIUS) }) {
            return Apple(appleLocation)
        }
    }
}

fun computeDistance(
    l1: Location,
    l2: Location,
    radius: Float
): Boolean {
    val deltaX = abs(l1.x - l2.x)
    val deltaY = abs(l1.y - l2.y)
    val distance = sqrt(deltaX.pow(2) + deltaY.pow(2))
    return distance <= radius
}

fun computeNewSnakePart(snakePart: SnakePart): SnakePart {
    val partLocation = snakePart.location.addDirection(snakePart.direction.getOpposite())
    return SnakePart(partLocation, snakePart.direction)
}