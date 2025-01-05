package com.example.myfootballcolection.data.firebase

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class EmailFirebaseAuthenticator {

    private val auth = Firebase.auth

    suspend fun signUpWithEmailPassword(
        email: String,
        password: String
    ) = auth.createUserWithEmailAndPassword(email, password).await().user

    suspend fun signInWithEmailPassword(
        email: String,
        password: String
    )  = auth.signInWithEmailAndPassword(email, password).await().user

    fun signOut(): Unit = auth.signOut()

    suspend fun updatePassword(
        newPassword: String
    ) = auth.currentUser?.updatePassword(newPassword)?.await()

    fun recoverPassword(
        email: String
    ) = auth.sendPasswordResetEmail(email)
}