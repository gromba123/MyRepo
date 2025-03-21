package com.example.myfootballcollection.domain.useCase.football

import android.util.Log
import com.example.myfootballcollection.domain.data.repository.FootballRepository
import com.example.myfootballcollection.domain.error.Error
import com.example.myfootballcollection.domain.error.Result
import com.example.myfootballcollection.domain.error.UserError
import com.example.myfootballcollection.domain.model.football.Team

class SearchTeamsUseCase(
    private val footballRepository: FootballRepository
) {
    suspend operator fun invoke(name: String): Result<List<Team>, Error> =
        try {
            Result.Success(footballRepository.getTeams(name).response.map { it.team })
        } catch (e: Exception) {
            e.message?.let { Log.v("Test", it) }
            Result.Error(UserError.Network.TIMEOUT)
            //TODO(Implement other exceptions)
        }
}