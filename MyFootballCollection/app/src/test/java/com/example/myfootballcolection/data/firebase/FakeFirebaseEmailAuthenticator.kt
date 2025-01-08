package com.example.myfootballcolection.data.firebase

import com.example.myfootballcolection.domain.firebase.FirebaseEmailAuthenticator
import com.google.firebase.auth.FirebaseUser

class FakeFirebaseEmailAuthenticator : FirebaseEmailAuthenticator {
    override suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser? {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser? {
        TODO("Not yet implemented")
    }

    override suspend fun updatePassword(newPassword: String) {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }

    override fun recoverPassword(email: String) {
        TODO("Not yet implemented")
    }
}