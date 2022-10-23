package pt.isel.pdm.chess4android.ui.screens.online.challenges.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import pt.isel.pdm.chess4android.dataAccess.ChallengesRepository
import pt.isel.pdm.chess4android.domain.online.ChallengeInfo
import javax.inject.Inject

/**
 * The View Model to be used in the [CreateChallengeActivity].
 *
 * Challenges are created by participants and are posted on the server, awaiting acceptance.
 */
@HiltViewModel
class CreateChallengeViewModel @Inject constructor(
    private val challengesRepository: ChallengesRepository
) : ViewModel() {

    private val _created: MutableLiveData<Result<ChallengeInfo>?> = MutableLiveData(null)
    val created: LiveData<Result<ChallengeInfo>?> = _created

    /**
     * Used to publish the acceptance state of the challenge
     */
    private val _accepted: MutableLiveData<Boolean> = MutableLiveData(false)
    val accepted: LiveData<Boolean> = _accepted

    /**
     * Creates a challenge with the given arguments. The result is placed in [created]
     */
    fun createChallenge(name: String, message: String) {
        challengesRepository.publishChallenge(
            name = name,
            message = message,
            onComplete = {
                _created.value = it
                it.onSuccess(::waitForAcceptance)
            }
        )
    }

    /**
     * Withdraws the current challenge from the list of available challenges.
     * @throws IllegalStateException if there's no challenge currently published
     */
    fun removeChallenge() {
        val currentChallenge = created.value
        check(currentChallenge != null && currentChallenge.isSuccess)
        subscription?.let { challengesRepository.unsubscribeToChallengeAcceptance(it) }
        currentChallenge.onSuccess {
            challengesRepository.withdrawChallenge(
                challengeId = it.id,
                onComplete = { _created.value = null }
            )
        }
    }

    /**
     * Lets cleanup. The view model is about to be destroyed.
     */
    override fun onCleared() {
        if (created.value != null && created.value?.isSuccess == true)
            removeChallenge()
    }

    private var subscription: ListenerRegistration? = null

    private fun waitForAcceptance(challengeInfo: ChallengeInfo) {
        subscription = challengesRepository.subscribeToChallengeAcceptance(
            challengeId = challengeInfo.id,
            onSubscriptionError = { _created.value = Result.failure(it) },
            onChallengeAccepted = { _accepted.value = true },
        )
    }
}