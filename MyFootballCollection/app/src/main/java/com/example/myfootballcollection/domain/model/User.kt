package com.example.myfootballcollection.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "user",
    indices = [
        Index(value = ["mail"], unique = true),
        Index(value = ["tag"], unique = true)
    ]
)
data class User(
    @PrimaryKey val id: String,
    val name: String,
    val mail: String,
    val tag: String,
    val profilePictureUrl: String,
    val followingTeams: List<String>
) {
    companion object {
        fun buildBlankUser(
            id: String,
            mail: String
        ): User {
            return User(
                id = id,
                name = "",
                mail = mail,
                tag = "",
                profilePictureUrl = "",
                followingTeams = emptyList()
            )
        }
    }
}

