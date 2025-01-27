package com.example.myfootballcollection.domain.useCase.user

import com.example.myfootballcollection.domain.firebase.FirebaseEmailAuthenticator

class IsFirebaseLoggedIn(
    private val firebaseEmailAuthenticator: FirebaseEmailAuthenticator
) {
    operator fun invoke(): Boolean = firebaseEmailAuthenticator.getCurrentUser() != null
}