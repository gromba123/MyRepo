package com.example.myfootballcollection

import androidx.lifecycle.ViewModel
import com.example.myfootballcollection.domain.useCase.user.UserUseCases
import com.example.myfootballcollection.navigation.AppAuth
import com.example.myfootballcollection.navigation.Screen

class BaseViewModel(
    private val userUseCases: UserUseCases
) : ViewModel() {

    fun isUserLoggedIn(): Screen =
        if(userUseCases.isFirebaseLoggedIn()) AppAuth.Create else AppAuth.Login
}