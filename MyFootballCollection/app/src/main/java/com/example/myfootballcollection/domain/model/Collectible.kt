package com.example.myfootballcollection.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "collectibles",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Game::class,
            parentColumns = ["id"],
            childColumns = ["gameId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Collectible(
    @PrimaryKey val id: String,
    val userId: String,
    val team: String,
    val season: String,
    val description: String,
    val bucketUrl: String,
    val gameId: String?
)