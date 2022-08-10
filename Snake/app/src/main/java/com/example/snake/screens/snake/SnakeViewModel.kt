package com.example.snake.screens.snake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snake.utils.Direction
import com.example.snake.utils.Location
import com.example.snake.utils.addDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

private val STARTING_LOCATION = Location(50F, 50F)

@HiltViewModel
class SnakeViewModel @Inject constructor() : ViewModel() {
    private var actualDirection = Direction.RIGHT
    private val _location = MutableLiveData<Location>(STARTING_LOCATION)
    val location: LiveData<Location> = _location
    private val moveFlow = flow<Unit> {
        while (true) {
            delay(1000L)
            _location.value = _location.value!!.addDirection(actualDirection.location)
        }
    }
    init {
        viewModelScope.launch {
            moveFlow.collect()
        }
    }

    fun changeDirection(direction: Direction) {
        actualDirection = direction
    }
}