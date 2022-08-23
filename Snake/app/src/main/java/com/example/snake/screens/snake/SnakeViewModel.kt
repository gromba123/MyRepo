package com.example.snake.screens.snake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snake.utils.Apple
import com.example.snake.utils.Direction
import com.example.snake.utils.SnakePart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SnakeViewModel @Inject constructor(
    private val snakeMovement: SnakeMovement
) : ViewModel() {

    private val _snake = MutableLiveData<List<SnakePart>>(listOf(snakeMovement.getSnakeHead()))
    val snake: LiveData<List<SnakePart>> = _snake

    private val _appleList = MutableLiveData<List<Apple>>(listOf())
    val appleList: LiveData<List<Apple>> = _appleList

    init {
        viewModelScope.launch {
            snakeMovement.positionFlow.collect {
                _snake.value = it.snakeParts
                _appleList.value = it.appleList
            }
        }
        viewModelScope.launch {
            snakeMovement.appleFlow.collect {
                _appleList.value = it
            }
        }
    }

    fun setMeasures(height: Float, width: Float) = snakeMovement.setMeasures(height, width)

    fun changeDirection(direction: Direction) {
        snakeMovement.changeDirection(direction)
    }
}