package com.example.myfootballcolection.data.firebase

import com.example.myfootballcolection.domain.firebase.FirebaseEmailAuthenticator
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class FirebaseEmailAuthenticatorImpl : FirebaseEmailAuthenticator {

    private val auth = Firebase.auth

    override suspend fun signUpWithEmailPassword(
        email: String,
        password: String
    ) = auth.createUserWithEmailAndPassword(email, password).await().user

    override suspend fun signInWithEmailPassword(
        email: String,
        password: String
    )  = auth.signInWithEmailAndPassword(email, password).await().user

    override fun signOut(): Unit = auth.signOut()

    override suspend fun updatePassword(
        newPassword: String
    ) {
        auth.currentUser?.updatePassword(newPassword)?.await()
    }

    override fun recoverPassword(
        email: String
    ) {
        auth.sendPasswordResetEmail(email)
    }
}