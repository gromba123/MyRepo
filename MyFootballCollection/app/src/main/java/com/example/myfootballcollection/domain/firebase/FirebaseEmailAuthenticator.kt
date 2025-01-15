package com.example.myfootballcollection.domain.firebase

import com.google.firebase.auth.FirebaseUser

interface FirebaseEmailAuthenticator {
    suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser?
    suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser?
    fun signOut(): Unit
    suspend fun updatePassword(newPassword: String)
    fun recoverPassword(email: String)
    suspend fun deleteUser()
}