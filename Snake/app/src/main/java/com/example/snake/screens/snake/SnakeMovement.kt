package com.example.snake.screens.snake

import android.util.Log
import com.example.snake.APP_TAG
import com.example.snake.utils.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

private const val REFRESH_RATE = 250L
private const val APPLE_GENERATION_RATE = 7500L

class SnakeMovement {

    private var height: Float? = null
    private var width: Float? = null
    private var gameElements: GameElements = GameElements()

    val positionFlow = flow {
        while (true) {
            delay(REFRESH_RATE)
            emit(moveSnake())
        }
    }

    val appleFlow = flow {
        while(true) {
            delay(APPLE_GENERATION_RATE)
            if (height != null && width != null) {
                val newAppleList = gameElements.appleList + generateApple(height!!, width!!, gameElements.snakeParts)
                gameElements = GameElements(gameElements.snakeParts, newAppleList, gameElements.locationsList)
                emit(newAppleList)
            }
        }
    }

    private fun moveSnake(): MovementUpdates {
        if (height == null || width == null) return MovementUpdates(gameElements.snakeParts, gameElements.appleList)
        val newSnakeParts = gameElements.snakeParts.map { snakePart ->
            val newLocation = snakePart.location.addDirection(snakePart.direction)
            val dirs = gameElements.locationsList.filter {
                it.location.x == newLocation.x &&  it.location.y == newLocation.y
            }
            if (dirs.isEmpty()) {
                SnakePart(
                    if (newLocation.x > width!!) Location(0F, newLocation.y)
                    else if (newLocation.x < 0) Location(width!!, newLocation.y)
                    else if (newLocation.y > height!!) Location(newLocation.x, 0F)
                    else if (newLocation.y < 0) Location(newLocation.x, height!!)
                    else newLocation,
                    snakePart.direction
                )
            } else {
                dirs[0].actualIndex += 1
                SnakePart(
                    newLocation,
                    dirs[0].direction
                )
            }
        }
        val newAppleList = gameElements.appleList.filter {
            !computeDistance(it.location, newSnakeParts[0].location, SNAKE_RADIUS)
        }
        val finalPartsList = if (newAppleList.size != gameElements.appleList.size) {
            newSnakeParts + computeNewSnakePart(newSnakeParts.last())
        } else {
            newSnakeParts
        }
        gameElements = GameElements(
            finalPartsList,
            newAppleList,
            gameElements.locationsList.filter { it.actualIndex < finalPartsList.size - 1}
        )
        Log.v(APP_TAG, "Locations: ${gameElements.locationsList.size}")
        return MovementUpdates(gameElements.snakeParts, gameElements.appleList)
    }

    fun getSnakeHead() = gameElements.snakeParts[0]

    fun setMeasures(height: Float, width: Float) {
        this.height = height
        this.width = width
    }

    fun changeDirection(direction: Direction) {
        if (
            direction != gameElements.snakeParts[0].direction &&
            !gameElements.snakeParts[0].direction.isOpposite(direction)
        ) {
            val newPartsList = gameElements.snakeParts.toMutableList()
            newPartsList[0] = SnakePart(gameElements.snakeParts[0].location, direction)
            val directionList =
                if (newPartsList.size != 1) gameElements.locationsList + DirectionChange(direction, newPartsList[0].location)
                else gameElements.locationsList
            gameElements = GameElements(
                newPartsList,
                gameElements.appleList,
                directionList
            )
        }
    }
}