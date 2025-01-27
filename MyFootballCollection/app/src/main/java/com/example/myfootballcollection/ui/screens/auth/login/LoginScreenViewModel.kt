package com.example.myfootballcollection.ui.screens.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfootballcollection.domain.error.Result
import com.example.myfootballcollection.domain.useCase.user.UserUseCases
import com.example.myfootballcollection.navigation.AppAuth
import com.example.myfootballcollection.navigation.AppScreen
import com.example.myfootballcollection.navigation.Screen
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val userUseCases: UserUseCases
) : ViewModel() {

    fun registerUser(
        mail: String,
        password: String,
        onSuccess: (Screen) -> Unit
    ) {
        viewModelScope.launch {
            when (val result = userUseCases.loginUser(mail, password)) {
                is Result.Success -> {
                    val screen: Screen = if (result.data.tag.isBlank()) {
                        AppAuth.Create
                    } else {
                        AppScreen.Social
                    }
                    onSuccess(screen)
                }
                else -> {
                    //TODO("Handle error")
                    Log.v("TEST", (result as Result.Error).error.toString())
                }
            }
        }
    }
}