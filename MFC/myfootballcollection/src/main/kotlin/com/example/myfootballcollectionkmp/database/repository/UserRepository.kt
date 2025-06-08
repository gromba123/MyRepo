package com.example.myfootballcollectionkmp.database.repository

import com.example.myfootballcollectionkmp.common.sqlExceptionFilter
import com.example.myfootballcollectionkmp.database.dao.UserDao
import com.example.myfootballcollectionkmp.domain.UserRegistration
import com.example.myfootballcollectionkmp.domain.UserUpdate
import org.springframework.stereotype.Component

@Component
class UserRepository(
    private val userDao: UserDao
) {
    //  create a user inside the database
    fun createUser(
        userRegistration: UserRegistration
    ) = sqlExceptionFilter {
        userDao.createUser(
            userRegistration.userId,
            userRegistration.mail
        )
    }

    //  create a user inside the database
    fun updateUser(
        user: UserUpdate
    ) = sqlExceptionFilter {
        userDao.updateUser(
            user.userId,
            user.name,
            user.tag,
            user.birthday,
            user.country,
            user.profilePictureUrl,
            user.headerPictureUrl,
            user.followingTeams
        )
    }

    fun getUser(userId: String) = null
}