package com.example.snake.screens.snake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snake.utils.Location
import com.example.snake.utils.addDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SnakeViewModel @Inject constructor() : ViewModel() {
    private val actualDirection = Location(10F, 0F)
    private val _location = MutableLiveData<Location>(Location(0F, 0F))
    val location: LiveData<Location> = _location
    private val moveFlow = flow<Unit> {
        while (true) {
            delay(2000L)
            _location.value = _location.value!!.addDirection(actualDirection)
        }
    }
    init {
        viewModelScope.launch {
            moveFlow.collect()
        }
    }
}