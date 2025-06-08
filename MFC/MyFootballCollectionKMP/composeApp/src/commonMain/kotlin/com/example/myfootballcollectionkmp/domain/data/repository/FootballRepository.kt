package com.example.myfootballcollectionkmp.domain.data.repository

import com.example.myfootballcollectionkmp.domain.model.network.FootballApiResponse
import com.example.myfootballcollectionkmp.domain.model.network.wrapper.FootballApiTeam

interface FootballRepository {
    suspend fun getTeams(name: String): FootballApiResponse<FootballApiTeam>
}