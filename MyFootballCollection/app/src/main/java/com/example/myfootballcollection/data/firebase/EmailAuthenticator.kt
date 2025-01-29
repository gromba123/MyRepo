package com.example.myfootballcollection.data.firebase

import com.example.myfootballcollection.domain.data.firebase.FirebaseEmailAuthenticator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
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

    override fun getCurrentUser(): FirebaseUser? = auth.currentUser


    override suspend fun updatePassword(
        newPassword: String
    ) {
        auth.currentUser?.updatePassword(newPassword)?.await()
    }

    override suspend fun deleteUser() {
        TODO("Not yet implemented")
    }

    override fun recoverPassword(
        email: String
    ) {
        auth.sendPasswordResetEmail(email)
    }

    override fun signOut(): Unit = auth.signOut()
}
