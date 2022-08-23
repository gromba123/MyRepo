package com.example.snake

import com.example.snake.screens.snake.SnakeMovement
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SnakeModule {
    @Provides
    @Singleton
    fun getSnakeMovement(): SnakeMovement = SnakeMovement()
}