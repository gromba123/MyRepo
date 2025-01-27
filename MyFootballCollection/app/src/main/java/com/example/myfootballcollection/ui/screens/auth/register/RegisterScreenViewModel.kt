package com.example.myfootballcollection.ui.screens.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfootballcollection.domain.error.Result
import com.example.myfootballcollection.domain.useCase.user.UserUseCases
import kotlinx.coroutines.launch

class RegisterScreenViewModel(
    private val userUseCases: UserUseCases
) : ViewModel() {

    fun registerUser(
        mail: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            when (userUseCases.registerUser(mail, password)) {
                is Result.Success -> {
                    onSuccess()
                }
                else -> {
                    //TODO("Handle error")
                }
            }
        }
    }
}