package pt.isel.pdm.chess4android.online.challenges.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pt.isel.pdm.chess4android.APP_TAG
import pt.isel.pdm.chess4android.PuzzleOfDayApplication
import pt.isel.pdm.chess4android.online.challenges.ChallengeInfo
import pt.isel.pdm.chess4android.online.games.OnlineGameState

/**
 * ViewModel for the [ChallengesListActivity]
 */
class ChallengesListViewModel(app: Application) : AndroidViewModel(app) {

    private val app = getApplication<PuzzleOfDayApplication>()

    private val _challenges: MutableLiveData<Result<List<ChallengeInfo>>> = MutableLiveData()
    val challenges: LiveData<Result<List<ChallengeInfo>>> = _challenges

    fun fetchChallenges() =
        app.challengesRepository.fetchChallenges(onComplete = {
            _challenges.value = it
        })

    private val _enrolmentResult: MutableLiveData<Result<Pair<ChallengeInfo, OnlineGameState>>?> = MutableLiveData()
    val enrolmentResult: LiveData<Result<Pair<ChallengeInfo, OnlineGameState>>?> = _enrolmentResult

    /**
     * Tries to accept a challenge of the challenge list. Removes that challenge from
     * the list and creates a game
     */
    fun tryAcceptChallenge(challengeInfo: ChallengeInfo) {
        Log.v(APP_TAG, "Challenge accepted. Signalling by removing challenge from list")
        app.challengesRepository.withdrawChallenge(
            challengeId = challengeInfo.id,
            onComplete = {
                it.onSuccess {
                    Log.v(APP_TAG, "We successfully unpublished the challenge. Let's start the game")
                    app.gamesRepository.createGame(challengeInfo, onComplete = { game ->
                        _enrolmentResult.value = game
                    })
                }
            }
        )
    }
}