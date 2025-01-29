package com.example.myfootballcollection.domain.useCase.football

import com.example.myfootballcollection.domain.data.repository.FootballRepository
import com.example.myfootballcollection.domain.error.Error
import com.example.myfootballcollection.domain.error.Result
import com.example.myfootballcollection.domain.error.UserError
import com.example.myfootballcollection.domain.model.Team

class SearchTeamsUseCase(
    private val footballRepository: FootballRepository
) {
    suspend operator fun invoke(name: String): Result<List<Team>, Error> =
        try {
            Result.Success(footballRepository.getTeams(name))
        } catch (e: Exception) {
            Result.Error(UserError.Network.TIMEOUT)
            //TODO(Implement other exceptions)
        }
}