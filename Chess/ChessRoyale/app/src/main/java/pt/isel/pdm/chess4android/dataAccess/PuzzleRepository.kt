package pt.isel.pdm.chess4android.dataAccess

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pt.isel.pdm.chess4android.domain.ServiceUnavailable
import pt.isel.pdm.chess4android.domain.puzzle.PuzzleDTO
import pt.isel.pdm.chess4android.domain.puzzle.Quiz
import pt.isel.pdm.chess4android.domain.puzzle.toQuiz
import javax.inject.Inject

/**
 * Works as a layer of abstraction that makes available a set of
 * methods to simplify the interactions with the Room DB and Lichess API
 * for a user to obtain a puzzle
 */
class PuzzleRepository @Inject constructor(
    private val service: PuzzleOfDayService,
    private val puzzleDao: PuzzleHistoryDatabase
) {

    /**
     * Gets the daily puzzle from the local DB, if available.
     */
    suspend fun maybeGetTodayPuzzleFromDB(): PuzzleDTO? = puzzleDao.getTodayPuzzle()

    /**
     * Gets the daily puzzle from the remote API.
     */
    private suspend fun getTodayPuzzleFromAPI(): Quiz =
        withContext(Dispatchers.IO) {
            val response = service.getQuiz().execute()
            val puzzle = response.body()
            if (puzzle != null && response.isSuccessful) puzzle
            else throw ServiceUnavailable()
        }

    /**
     * Saves the daily puzzle to the local DB.
     */
    private suspend fun saveToDB(dto: Quiz) =
        puzzleDao.insert(
            PuzzleEntity(
                id = dto.puzzle.id,
                pgn = dto.game.pgn,
                sln = dto.puzzle.solution.reduce { acc, s -> "$acc $s" },
                state = 0,
                actualPgn = "",
                actualSln = ""
            )
        )

    /**
     * Gets the puzzle of day, either from the local DB, if available, or from
     * the remote API.
     */
    suspend fun fetchPuzzleOfDay(): Quiz {
        val maybePuzzle = maybeGetTodayPuzzleFromDB()
        return if (maybePuzzle != null) maybePuzzle.toQuiz()
        else {
            val todayPuzzle = getTodayPuzzleFromAPI()
            saveToDB(todayPuzzle)
            return todayPuzzle
        }
    }

    suspend fun getPuzzle(id: String) = puzzleDao.getPuzzle(id)

    suspend fun updatePuzzle(
        id: String,
        pgn: String,
        sln: String,
        state: Int
    ) = puzzleDao.updatePuzzle(id, pgn, sln, state)

    suspend fun deleteAll() = puzzleDao.deleteAll()
}