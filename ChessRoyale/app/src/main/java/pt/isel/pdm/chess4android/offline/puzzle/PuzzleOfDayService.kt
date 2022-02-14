package pt.isel.pdm.chess4android.offline.puzzle

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.Call
import retrofit2.http.GET

const val API_URL = "https://lichess.org/api/"

@Parcelize
data class Quiz(val game: Game, val puzzle: Puzzle) : Parcelable

@Parcelize
data class Game(val pgn: String) : Parcelable

@Parcelize
data class Puzzle(val solution: List<String>, val id: String) : Parcelable

/**
 * Represents a Data Transfers Object
 * to obtain information of the puzzles from the Room DB
 */
@Parcelize
data class PuzzleDTO(val id: String,
                     val pgn: String,
                     val sln: String,
                     val actualPgn: String,
                     val actualSln: String) : Parcelable

/**
 * Represents a Data Transfers Object to obtain
 * partial information of the puzzles from the Room DB
 */
@Parcelize
data class PuzzleHistoryDTO(val id: String,
                            val timestamp: String,
                            val state: Int) : Parcelable

/**
 * Service for the Retrofit API
 */
interface PuzzleOfDayService {
    @GET("puzzle/daily")
    fun getQuiz(): Call<Quiz>
}

class ServiceUnavailable(message: String = "", cause: Throwable? = null) : Exception(message, cause)