package com.example.myfootballcollectionkmp.domain.data.repository

import com.example.myfootballcollectionkmp.domain.model.network.Response
import com.example.myfootballcollectionkmp.domain.model.network.wrapper.TeamWrapper

interface FootballRepository {
    suspend fun getTeams(name: String): Response<TeamWrapper>
}