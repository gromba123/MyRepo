package pt.isel.pdm.chess4android.dataAccess

import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import pt.isel.pdm.chess4android.domain.online.ChallengeInfo
import pt.isel.pdm.chess4android.domain.online.OnlineBoard
import pt.isel.pdm.chess4android.domain.online.OnlineGameState
import pt.isel.pdm.chess4android.domain.online.toGameState
import pt.isel.pdm.chess4android.domain.pieces.Team
import javax.inject.Inject

private const val GAMES_COLLECTION = "games"
private const val GAME_STATE_KEY = "game"

class GamesRepository @Inject constructor(
    private val mapper: Gson
) {

    fun createGame(
        challenge: ChallengeInfo,
        onComplete: (Result<Pair<ChallengeInfo, OnlineGameState>>) -> Unit
    ) {
        val gameState = OnlineBoard(playerTeam = Team.BLACK).toGameState(challenge.id, "")
        Firebase.firestore.collection(GAMES_COLLECTION)
            .document(challenge.id)
            .set(hashMapOf(GAME_STATE_KEY to mapper.toJson(gameState)))
            .addOnSuccessListener { onComplete(Result.success(Pair(challenge, gameState))) }
            .addOnFailureListener { onComplete(Result.failure(it)) }
    }

    fun updateGameState(gameState: OnlineGameState, onComplete: (Result<OnlineGameState>) -> Unit) {
        Firebase.firestore.collection(GAMES_COLLECTION)
            .document(gameState.id)
            .set(hashMapOf(GAME_STATE_KEY to mapper.toJson(gameState)))
            .addOnSuccessListener { onComplete(Result.success(gameState)) }
            .addOnFailureListener { onComplete(Result.failure(it)) }
    }

    fun subscribeToGameStateChanges(
        challengeId: String,
        onSubscriptionError: (Exception) -> Unit,
        onGameStateChange: (OnlineGameState) -> Unit
    ): ListenerRegistration {

        return Firebase.firestore
            .collection(GAMES_COLLECTION)
            .document(challengeId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    onSubscriptionError(error)
                    return@addSnapshotListener
                }

                if (snapshot?.exists() == true) {
                    val gameState = mapper.fromJson(
                        snapshot.get(GAME_STATE_KEY) as String,
                        OnlineGameState::class.java
                    )
                    onGameStateChange(gameState)
                }
            }
    }

    fun deleteGame(challengeId: String, onComplete: (Result<Unit>) -> Unit) {
        Firebase.firestore.collection(GAMES_COLLECTION)
            .document(challengeId)
            .delete()
            .addOnSuccessListener { onComplete(Result.success(Unit)) }
            .addOnFailureListener { onComplete(Result.failure(it)) }
    }
}