package pt.isel.pdm.chess4android.offline.history

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pt.isel.pdm.chess4android.offline.puzzle.*

fun PuzzleDTO.toQuiz() = Quiz(Game(pgn), Puzzle(sln.split(" "), id))

/**
 * Works as a layer of abstraction that makes available a set of
 * methods to simplify the interactions with the Room DB and Lichess API
 * for a user to obtain a puzzle
 */
class PuzzleRepository (
    private val service: PuzzleOfDayService,
    private val puzzleDao: PuzzleHistory
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
}