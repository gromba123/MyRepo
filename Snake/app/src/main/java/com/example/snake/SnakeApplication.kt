package com.example.snake

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

const val APP_TAG = "SnakeApplicationTag"

@HiltAndroidApp
class SnakeApplication : Application()