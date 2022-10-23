package pt.isel.pdm.chess4android.dataAccess

import pt.isel.pdm.chess4android.domain.puzzle.Quiz
import retrofit2.Call
import retrofit2.http.GET

const val API_URL = "https://lichess.org/api/"

/**
 * Service for the Retrofit API
 */
interface PuzzleOfDayService {
    @GET("puzzle/daily")
    fun getQuiz(): Call<Quiz>
}