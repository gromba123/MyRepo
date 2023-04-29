package pt.isel.pdm.chess4android.ui.screens.online.challenges.list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pt.isel.pdm.chess4android.APP_TAG
import pt.isel.pdm.chess4android.dataAccess.ChallengesRepository
import pt.isel.pdm.chess4android.dataAccess.GamesRepository
import pt.isel.pdm.chess4android.domain.ScreenState
import pt.isel.pdm.chess4android.domain.online.ChallengeInfo
import pt.isel.pdm.chess4android.domain.online.OnlineGameState
import javax.inject.Inject

@HiltViewModel
class ChallengesListViewModel @Inject constructor(
    private val challengesRepository: ChallengesRepository,
    private val gamesRepository: GamesRepository
) : ViewModel() {

    private val _screen: MutableState<ScreenState> = mutableStateOf(ScreenState.Loading)
    val screen: State<ScreenState> = _screen

    private val _challenges: MutableState<List<ChallengeInfo>> = mutableStateOf(listOf())
    val challenges: State<List<ChallengeInfo>> = _challenges

    private val _selectedChallenge: MutableState<ChallengeInfo?> = mutableStateOf(null)
    val selectedChallenge: State<ChallengeInfo?> = _selectedChallenge

    init {
        fetchChallenges()
    }

    private fun fetchChallenges() = challengesRepository
        .fetchChallenges {
            it
                .onSuccess { list ->
                    _challenges.value = list
                    _screen.value = ScreenState.Loaded
                }
                .onFailure {
                    _screen.value = ScreenState.Loaded
                    //TODO("Error handling")
                }
        }

    fun onAcceptChallenge(challengeInfo: ChallengeInfo) {
        _selectedChallenge.value = challengeInfo
    }

    fun cleanSelection() {
        _selectedChallenge.value = null
    }

    /**
     * Tries to accept a challenge of the challenge list. Removes that challenge from
     * the list and creates a game
     */
    fun tryAcceptChallenge(
        challengeInfo: ChallengeInfo,
        onAccepted: (Result<Pair<ChallengeInfo, OnlineGameState>>) -> Unit
    ) {
        Log.v(APP_TAG, "Challenge accepted. Signalling by removing challenge from list")
        challengesRepository.withdrawChallenge(
            challengeId = challengeInfo.id,
            onComplete = {
                it.onSuccess {
                    Log.v(APP_TAG, "We successfully unpublished the challenge. Let's start the game")
                    gamesRepository.createGame(challengeInfo) { game ->
                        onAccepted(game)
                    }
                }
            }
        )
    }
}