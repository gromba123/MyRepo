package com.example.myfootballcollectionkmp.domain.data.firebase

import com.google.firebase.auth.FirebaseUser

interface FirebaseEmailAuthenticator {
    suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser?
    suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser?
    fun getCurrentUser(): FirebaseUser?
    suspend fun updatePassword(newPassword: String)
    suspend fun deleteUser()
    fun recoverPassword(email: String)
    fun signOut()
}