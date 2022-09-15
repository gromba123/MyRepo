package pt.isel.pdm.chess4android.offline.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import pt.isel.pdm.chess4android.PuzzleOfDayApplication
import pt.isel.pdm.chess4android.offline.puzzle.PuzzleDTO
import pt.isel.pdm.chess4android.offline.puzzle.PuzzleHistoryDTO
import javax.inject.Inject

/**
 * ViewModel for the [PuzzleHistoryActivity]
 */
@HiltViewModel
class HistoryActivityViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val historyDao : PuzzleHistory by lazy {
        getApplication<PuzzleOfDayApplication>().historyDb.getPuzzleHistoryDao()
    }

    /**
     * Holds a [LiveData] with the list of quotes
     */
    val history: LiveData<List<PuzzleHistoryDTO>> = liveData {
        val quotes = historyDao.getAll()
        emit(quotes)
    }

    /**
     * Returns a [LiveData] that loads a puzzle from the local DB
     */
    fun loadPuzzle(puzzle: PuzzleHistoryDTO): LiveData<PuzzleDTO?> {
        return liveData {
            val puzzleDTO = historyDao.getPuzzle(puzzle.id)
            emit(puzzleDTO)
        }
    }

    fun fetchDailyPuzzle(): LiveData<PuzzleDTO?> {
        return liveData {
            val app = getApplication<PuzzleOfDayApplication>()
            app.puzzleRepository.fetchPuzzleOfDay()
            emit(app.puzzleRepository.maybeGetTodayPuzzleFromDB())
        }
    }
}