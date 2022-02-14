package pt.isel.pdm.chess4android.online.challenges

import android.util.Log
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pt.isel.pdm.chess4android.APP_TAG

private const val CHALLENGES_COLLECTION = "challenges"

private const val CHALLENGER_NAME = "challengerName"
private const val CHALLENGER_MESSAGE = "challengerMessage"

/**
 * Repository access the Firestore DB
 */
class ChallengesRepository {

    /**
     * Obtains the list of challenges
     */
    fun fetchChallenges(onComplete: (Result<List<ChallengeInfo>>) -> Unit) {
        val limit = 30
        Firebase.firestore.collection(CHALLENGES_COLLECTION)
            .get()
            .addOnSuccessListener { result ->
                Log.v(APP_TAG, "Repo got list from Firestore")
                onComplete(Result.success(result.take(limit).map { it.toChallengeInfo() }))
            }
            .addOnFailureListener {
                Log.e(APP_TAG, "Repo: An error occurred while fetching list from Firestore")
                Log.e(APP_TAG, "Error was $it")
                onComplete(Result.failure(it))
            }
    }

    /**
     * Publishes a new challenge into the DB
     */
    fun publishChallenge(
        name: String,
        message: String,
        onComplete: (Result<ChallengeInfo>) -> Unit
    ) {
        Firebase.firestore.collection(CHALLENGES_COLLECTION)
            .add(hashMapOf(CHALLENGER_NAME to name, CHALLENGER_MESSAGE to message))
            .addOnSuccessListener {
                onComplete(Result.success(ChallengeInfo(it.id, name, message)))
            }
            .addOnFailureListener { onComplete(Result.failure(it)) }
    }

    /**
     * Deletes all challenges
     */
    fun deleteAll(list: List<ChallengeInfo>): Unit {
        list.forEach {
            Firebase.firestore
                .collection(CHALLENGES_COLLECTION)
                .document(it.id)
                .delete()
        }
    }

    /**
     * Withdraws a challenge from the challenges collection
     */
    fun withdrawChallenge(challengeId: String, onComplete: (Result<Unit>) -> Unit) {
        Firebase.firestore
            .collection(CHALLENGES_COLLECTION)
            .document(challengeId)
            .delete()
            .addOnSuccessListener { onComplete(Result.success(Unit)) }
            .addOnFailureListener { onComplete(Result.failure(it)) }
    }

    /**
     * Subscribes the document to be notified
     * when the game starts and to initiate the
     * board
     */
    fun subscribeToChallengeAcceptance(
        challengeId: String,
        onSubscriptionError: (Exception) -> Unit,
        onChallengeAccepted: () -> Unit
    ): ListenerRegistration {

        return Firebase.firestore
            .collection(CHALLENGES_COLLECTION)
            .document(challengeId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    onSubscriptionError(error)
                    return@addSnapshotListener
                }

                if (snapshot?.exists() == false) {
                    // Document has been removed, thereby signalling that someone accepted
                    // the challenge
                    onChallengeAccepted()
                }
            }
    }

    /**
     * Cancels the subscription to a document
     */
    fun unsubscribeToChallengeAcceptance(subscription: ListenerRegistration) {
        subscription.remove()
    }
}

/**
 * Extension function used to convert createdChallenge documents stored in the Firestore DB into
 * [ChallengeInfo] instances
 */
private fun QueryDocumentSnapshot.toChallengeInfo() =
    ChallengeInfo(
        id,
        data[CHALLENGER_NAME] as String,
        data[CHALLENGER_MESSAGE] as String
    )