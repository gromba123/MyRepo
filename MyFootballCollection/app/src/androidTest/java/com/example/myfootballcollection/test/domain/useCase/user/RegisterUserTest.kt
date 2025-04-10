package com.example.myfootballcollection.test.domain.useCase.user

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myfootballcollection.data.dataSource.MFCCollectionDatabase
import com.example.myfootballcollection.data.dataSource.getFakeDao
import com.example.myfootballcollection.data.firebase.FakeFirebaseEmailAuthenticator
import com.example.myfootballcollection.data.repository.UserRepositoryImpl
import com.example.myfootballcollection.domain.error.Result
import com.example.myfootballcollection.domain.useCase.user.RegisterUser
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterUserTest {
    private lateinit var fakeFirebaseEmailAuthenticator: FakeFirebaseEmailAuthenticator
    private lateinit var db: MFCCollectionDatabase
    private lateinit var registerUser: RegisterUser

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        fakeFirebaseEmailAuthenticator = FakeFirebaseEmailAuthenticator()
        db = getFakeDao(context)
        val dao = db.getMFCCollectionDao()
        registerUser = RegisterUser(
            UserRepositoryImpl(
                dao,
                fakeFirebaseEmailAuthenticator
            )
        )
    }

    @Test
    fun insertNewUser(): Unit = runBlocking {
        val email = "test@gmail.com"
        val password = "testing"
        val user = registerUser(email, password)
        Truth.assertThat(user).isInstanceOf(Result.Success::class.java)
    }

    @After
    fun tearDown() {
        runBlocking {
            fakeFirebaseEmailAuthenticator.deleteUser()
        }
        db.close()
    }
}