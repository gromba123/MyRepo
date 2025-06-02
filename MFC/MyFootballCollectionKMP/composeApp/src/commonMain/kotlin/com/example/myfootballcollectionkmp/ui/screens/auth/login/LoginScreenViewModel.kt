package com.example.myfootballcollectionkmp.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfootballcollectionkmp.domain.error.Result
import com.example.myfootballcollectionkmp.domain.useCase.user.UserUseCases
import com.example.myfootballcollectionkmp.navigation.AppAuth
import com.example.myfootballcollectionkmp.navigation.AppScreen
import com.example.myfootballcollectionkmp.navigation.Screen
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
                }
            }
        }
    }
}