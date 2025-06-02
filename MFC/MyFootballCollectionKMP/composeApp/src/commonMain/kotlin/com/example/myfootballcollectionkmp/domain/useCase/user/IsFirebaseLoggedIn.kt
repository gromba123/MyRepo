package com.example.myfootballcollectionkmp.domain.useCase.user

import com.example.myfootballcollectionkmp.domain.data.firebase.FirebaseEmailAuthenticator

class IsFirebaseLoggedIn(
    private val firebaseEmailAuthenticator: FirebaseEmailAuthenticator
) {
    operator fun invoke(): Boolean = firebaseEmailAuthenticator.getCurrentUser() != null
}