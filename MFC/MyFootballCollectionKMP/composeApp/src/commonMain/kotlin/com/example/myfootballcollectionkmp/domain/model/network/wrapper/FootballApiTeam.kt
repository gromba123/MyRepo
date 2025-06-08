package com.example.myfootballcollectionkmp.domain.model.network.wrapper

import com.example.myfootballcollectionkmp.domain.model.football.Team
import com.example.myfootballcollectionkmp.domain.model.football.Venue
import kotlinx.serialization.Serializable

@Serializable
data class FootballApiTeam(
    val team: Team,
    val venue: Venue? = null
)