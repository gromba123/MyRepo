package com.example.myfootballcolection.domain

data class CollectionItem(
    val id: Long = 0,
    val team: String,
    val season: String,
    val description: String,
    val bucketUrl: String,
    val gameId: String?
)