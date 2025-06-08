package com.example.myfootballcollectionkmp.database.dao

import org.jdbi.v3.sqlobject.statement.SqlCall
import org.springframework.stereotype.Component

private const val CREATE_USER = "CALL create_user(?,?)"
private const val UPDATE_USER = "CALL update_user(?,?,?,?,?,?,?,?,?)"

@Component
interface UserDao {

    @SqlCall(CREATE_USER)
    fun createUser(
        userId: String,
        mail: String
    )

    @SqlCall(UPDATE_USER)
    fun updateUser(
        userId: String,
        name: String,
        tag: String,
        birthday: String,
        country: String,
        profilePictureUrl: String,
        headerPictureUrl: String,
        followingTeams: List<String>
    )
}
