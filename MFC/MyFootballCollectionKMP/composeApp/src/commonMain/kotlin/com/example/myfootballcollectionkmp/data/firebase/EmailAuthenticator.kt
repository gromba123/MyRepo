package com.example.myfootballcollectionkmp.data.firebase

import com.example.myfootballcollectionkmp.domain.data.firebase.FirebaseEmailAuthenticator
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth

class FirebaseEmailAuthenticatorImpl : FirebaseEmailAuthenticator {

    private val auth = Firebase.auth

    override suspend fun signUpWithEmailPassword(
        email: String,
        password: String
    ) = auth.createUserWithEmailAndPassword(email, password).user

    override suspend fun signInWithEmailPassword(
        email: String,
        password: String
    )  = auth.signInWithEmailAndPassword(email, password).user

    override fun getCurrentUser(): FirebaseUser? = auth.currentUser


    override suspend fun updatePassword(
        newPassword: String
    ) {
        auth.currentUser?.updatePassword(newPassword)
    }

    override suspend fun deleteUser() {
        TODO("Not yet implemented")
    }

    override suspend fun recoverPassword(
        email: String
    ) {
        auth.sendPasswordResetEmail(email)
    }

    override suspend fun signOut(): Unit = auth.signOut()
}
