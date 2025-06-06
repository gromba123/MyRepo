package com.example.myfootballcollectionkmp.database.repository

import com.example.myfootballcollectionkmp.common.sqlExceptionFilter
import com.example.myfootballcollectionkmp.database.dao.UserDao
import com.example.myfootballcollectionkmp.domain.User
import org.springframework.stereotype.Component

@Component
class UserRepository(
    private val userDao: UserDao
) {
    //  create a user inside the database
    fun createUser(
        user: User
    ) = sqlExceptionFilter {
        userDao.createUser(
            user.userId,
            user.mail,
            user.name,
            user.tag,
            user.birthday,
            user.country,
            user.profilePictureUrl,
            user.headerPictureUrl,
            user.followingTeams,
        )
    }

    fun getUser(userId: String) = null
}