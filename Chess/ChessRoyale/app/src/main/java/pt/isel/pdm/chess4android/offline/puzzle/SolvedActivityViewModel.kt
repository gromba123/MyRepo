package pt.isel.pdm.chess4android.offline.puzzle

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for the [SolvedActivity]
 */
@HiltViewModel
class SolvedActivityViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private var puzzle: PuzzleDTO? = null

    private val _board: MutableLiveData<PuzzleBoard> = MutableLiveData()
    val board = _board

    /**
     * Builds a puzzle based based on a given pgn
     */
    fun buildPuzzle(puzzle: PuzzleDTO) {
        this.puzzle = puzzle
        val list = puzzle.pgn.split(" ")
        _board.value = (Parser()).parsePGN(list, list.size % 2 != 0)
    }

    /**
     * Loads the solution of the new puzzle
     */
    fun loadSolution() {
        puzzle?.let { puzzle ->
            val list = puzzle.actualPgn.split(" ")
            val toRotate = puzzle.pgn.split(" ").size % 2 != 0
            _board.value = (Parser()).parsePGN(list, toRotate)
        }
    }

    /**
     * Builds a puzzle based based on a given pgn
     */
    fun loadPuzzle() {
        puzzle?.let {
            val list = it.pgn.split(" ")
            _board.value = (Parser()).parsePGN(list, list.size % 2 != 0)
        }
    }
}