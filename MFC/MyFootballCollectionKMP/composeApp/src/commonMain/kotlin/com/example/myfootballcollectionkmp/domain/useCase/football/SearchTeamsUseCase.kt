package com.example.myfootballcollectionkmp.domain.useCase.football

import com.example.myfootballcollectionkmp.domain.data.repository.FootballRepository
import com.example.myfootballcollectionkmp.domain.error.Error
import com.example.myfootballcollectionkmp.domain.error.Result
import com.example.myfootballcollectionkmp.domain.error.UserError
import com.example.myfootballcollectionkmp.domain.model.football.Team

class SearchTeamsUseCase(
    private val footballRepository: FootballRepository
) {
    suspend operator fun invoke(name: String): Result<List<Team>, Error> =
        try {
            Result.Success(footballRepository.getTeams(name).response.map { it.team })
        } catch (e: Exception) {
            Result.Error(UserError.Network.TIMEOUT)
            //TODO(Implement other exceptions)
        }
}