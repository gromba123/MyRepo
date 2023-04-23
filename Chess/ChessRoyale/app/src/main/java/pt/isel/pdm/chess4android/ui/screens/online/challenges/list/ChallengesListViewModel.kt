package pt.isel.pdm.chess4android.ui.screens.online.challenges.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pt.isel.pdm.chess4android.APP_TAG
import pt.isel.pdm.chess4android.dataAccess.ChallengesRepository
import pt.isel.pdm.chess4android.dataAccess.GamesRepository
import pt.isel.pdm.chess4android.domain.ScreenState
import pt.isel.pdm.chess4android.domain.online.ChallengeInfo
import pt.isel.pdm.chess4android.domain.online.OnlineGameState
import javax.inject.Inject

/**
 * ViewModel for the [ChallengesListActivity]
 */
@HiltViewModel
class ChallengesListViewModel @Inject constructor(
    private val challengesRepository: ChallengesRepository,
    private val gamesRepository: GamesRepository
) : ViewModel() {

    private val _screen: MutableLiveData<ScreenState> = MutableLiveData(ScreenState.Loading)
    val screen: LiveData<ScreenState> = _screen

    private val _challenges: MutableLiveData<List<ChallengeInfo>> = MutableLiveData()
    val challenges: LiveData<List<ChallengeInfo>> = _challenges

    fun fetchChallenges() = challengesRepository.fetchChallenges {
            it
                .onSuccess { list ->
                    _challenges.value = list
                    _screen.value = ScreenState.Loaded
                }
                .onFailure {
                    //TODO("Error handling")
                }
        }

    private val _enrolmentResult: MutableLiveData<Result<Pair<ChallengeInfo, OnlineGameState>>?> = MutableLiveData()
    val enrolmentResult: LiveData<Result<Pair<ChallengeInfo, OnlineGameState>>?> = _enrolmentResult

    /**
     * Tries to accept a challenge of the challenge list. Removes that challenge from
     * the list and creates a game
     */
    fun tryAcceptChallenge(challengeInfo: ChallengeInfo) {
        Log.v(APP_TAG, "Challenge accepted. Signalling by removing challenge from list")
        challengesRepository.withdrawChallenge(
            challengeId = challengeInfo.id,
            onComplete = {
                it.onSuccess {
                    Log.v(APP_TAG, "We successfully unpublished the challenge. Let's start the game")
                    gamesRepository.createGame(challengeInfo, onComplete = { game ->
                        _enrolmentResult.value = game
                    })
                }
            }
        )
    }
}