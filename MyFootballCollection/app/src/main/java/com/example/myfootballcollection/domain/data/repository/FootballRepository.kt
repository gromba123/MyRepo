package com.example.myfootballcollection.domain.data.repository

import com.example.myfootballcollection.domain.model.Team

interface FootballRepository {
    suspend fun getTeams(name: String): List<Team>
}