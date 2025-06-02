package com.example.myfootballcollectionkmp

import androidx.lifecycle.ViewModel
import com.example.myfootballcollectionkmp.domain.useCase.user.UserUseCases
import com.example.myfootballcollectionkmp.navigation.AppAuth
import com.example.myfootballcollectionkmp.navigation.Screen

class BaseViewModel(
    private val userUseCases: UserUseCases
) : ViewModel() {

    fun isUserLoggedIn(): Screen =
        if(userUseCases.isFirebaseLoggedIn()) AppAuth.Create else AppAuth.Login
}