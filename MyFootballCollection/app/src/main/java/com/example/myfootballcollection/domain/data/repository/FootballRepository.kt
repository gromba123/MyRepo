package com.example.myfootballcollection.domain.data.repository

import com.example.myfootballcollection.domain.model.network.Response
import com.example.myfootballcollection.domain.model.network.wrapper.TeamWrapper

interface FootballRepository {
    suspend fun getTeams(name: String): Response<TeamWrapper>
}