package com.example.myfootballcollectionkmp.domain.data.firebase

import dev.gitlive.firebase.auth.FirebaseUser

interface FirebaseEmailAuthenticator {
    suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser?
    suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser?
    fun getCurrentUser(): FirebaseUser?
    suspend fun updatePassword(newPassword: String)
    suspend fun deleteUser()
    suspend fun recoverPassword(email: String)
    suspend fun signOut()
}