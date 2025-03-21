package com.example.myfootballcollection.domain.model.network.wrapper

import com.example.myfootballcollection.domain.model.football.Team
import com.example.myfootballcollection.domain.model.football.Venue
import kotlinx.serialization.Serializable

@Serializable
data class TeamWrapper(
    val team: Team,
    val venue: Venue? = null
)