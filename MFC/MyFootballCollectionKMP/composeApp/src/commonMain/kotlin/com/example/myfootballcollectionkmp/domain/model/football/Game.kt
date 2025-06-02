package com.example.myfootballcollectionkmp.domain.model.football

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.myfootballcollectionkmp.domain.model.user.User

@Entity(
    tableName = "user_games",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId", "gameId"], unique = true)]
)
data class Game(
    @PrimaryKey val id: String,
    val userId: String,
    val gameId: String
)