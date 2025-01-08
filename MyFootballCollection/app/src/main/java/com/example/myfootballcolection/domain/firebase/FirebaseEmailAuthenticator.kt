package com.example.myfootballcolection.domain.firebase

import com.google.firebase.auth.FirebaseUser

interface FirebaseEmailAuthenticator {

    suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser?

    suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser?

    suspend fun updatePassword(newPassword: String)

    fun signOut()

    fun recoverPassword(email: String)
}