package com.example.myfootballcollectionkmp

import androidx.compose.ui.window.ComposeUIViewController
import com.example.myfootballcollectionkmp.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}