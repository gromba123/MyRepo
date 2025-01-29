package com.example.myfootballcollection.data.firebase

import com.example.myfootballcollection.domain.error.OPERATION_NOT_ALLOWED_CODE
import com.example.myfootballcollection.domain.error.USER_ALREADY_EXISTS_CODE
import com.example.myfootballcollection.domain.error.USER_NOT_FOUND_CODE
import com.example.myfootballcollection.domain.error.WRONG_PASSWORD_CODE
import com.example.myfootballcollection.domain.data.firebase.FirebaseEmailAuthenticator
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser

const val TEST_MAIL = "TEST_PASSWORD"
const val TEST_PASSWORD = "TEST_PASSWORD"

class FakeFirebaseEmailAuthenticator : FirebaseEmailAuthenticator {

    private var firebaseUser: FakeFirebaseUser? = null

    override suspend fun signUpWithEmailPassword(
        email: String,
        password: String
    ): FirebaseUser? {
        firebaseUser?.let {
            if (it.email == email) {
                throw FirebaseAuthException(USER_ALREADY_EXISTS_CODE, "Test")
            }
        }
        firebaseUser = FakeFirebaseUser(email, password, "123")
        return firebaseUser
    }

    override suspend fun signInWithEmailPassword(
        email: String,
        password: String
    ): FirebaseUser? {
        if (firebaseUser == null) {
            if (password != TEST_MAIL) {
                FirebaseAuthException(USER_NOT_FOUND_CODE, "Test")
            }
            if (password != TEST_PASSWORD) {
                FirebaseAuthException(WRONG_PASSWORD_CODE, "Test")
            }
            firebaseUser = FakeFirebaseUser(email, password, "123")
            return firebaseUser
        }
        throw FirebaseAuthException(OPERATION_NOT_ALLOWED_CODE, "Test")
    }

    override fun getCurrentUser(): FirebaseUser? {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        firebaseUser = null
    }

    override suspend fun updatePassword(
        newPassword: String
    ) {
        firebaseUser?.changePassword(newPassword)
    }

    override fun recoverPassword(
        email: String
    ) {
        //TODO()
    }

    override suspend fun deleteUser() {
        //Do nothing
    }
}