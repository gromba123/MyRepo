package pt.isel.pdm.chess4android.ui.screens.online.challenges.create

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.isel.pdm.chess4android.dataAccess.ChallengesRepository
import pt.isel.pdm.chess4android.domain.ScreenState
import pt.isel.pdm.chess4android.domain.online.ChallengeInfo
import javax.inject.Inject

/**
 * Challenges are created by participants and are posted on the server, awaiting acceptance.
 */
@HiltViewModel
class CreateChallengeViewModel @Inject constructor(
    private val challengesRepository: ChallengesRepository
) : ViewModel() {

    private val _screenState: MutableState<ScreenState> = mutableStateOf(ScreenState.Loaded)
    val screenState: State<ScreenState> = _screenState

    private var _created: Result<ChallengeInfo>? = null
    private var subscription: ListenerRegistration? = null

    /**
     * Creates a challenge with the given arguments. The result is placed in [_created]
     */
    fun createChallenge(
        name: String,
        message: String,
        onAccept: (ChallengeInfo) -> Unit
    ) {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading
            challengesRepository.publishChallenge(
                name = name,
                message = message,
                onComplete = {
                    _created = it
                    it
                        .onSuccess { info -> waitForAcceptance(info, onAccept) }
                        .onFailure { /*TODO(Add Error)*/ }
                }
            )
        }
    }

    private fun waitForAcceptance(
        challengeInfo: ChallengeInfo,
        onAccept: (ChallengeInfo) -> Unit
    ) {
        subscription = challengesRepository.subscribeToChallengeAcceptance(
            challengeId = challengeInfo.id,
            onSubscriptionError = { /*TODO(Add Error)*/ },
            onChallengeAccepted = { onAccept(challengeInfo) },
        )
    }

    /**
     * Withdraws the current challenge from the list of available challenges.
     * @throws IllegalStateException if there's no challenge currently published
     */
    fun removeChallenge() {
        val currentChallenge = _created
        check(currentChallenge != null && currentChallenge.isSuccess)
        subscription?.let { challengesRepository.unsubscribeToChallengeAcceptance(it) }
        currentChallenge.onSuccess {
            challengesRepository.withdrawChallenge(
                challengeId = it.id,
                onComplete = {
                    _created = null
                    _screenState.value = ScreenState.Loaded
                }
            )
        }
    }

    /**
     * Lets cleanup. The view model is about to be destroyed.
     */
    override fun onCleared() {
        if (_created != null && _created?.isSuccess == true) {
            removeChallenge()
        }
    }
}