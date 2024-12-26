package com.example.myfootballcolection.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val tag: String,
    val profilePictureUrl: String,
    val followingTeams: List<String>
)