package pt.isel.pdm.chess4android.ui.screens.offline.puzzle

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.isel.pdm.chess4android.dataAccess.PuzzleRepository
import pt.isel.pdm.chess4android.domain.ScreenState
import pt.isel.pdm.chess4android.domain.puzzle.Parser
import pt.isel.pdm.chess4android.domain.puzzle.PuzzleBoard
import pt.isel.pdm.chess4android.domain.puzzle.PuzzleDTO
import javax.inject.Inject

@HiltViewModel
class SolvedScreenViewModel @Inject constructor(
    private val puzzleRepository: PuzzleRepository
) : ViewModel() {

    private lateinit var puzzle: PuzzleDTO

    private val _board: MutableState<PuzzleBoard?> = mutableStateOf(null)
    val board: State<PuzzleBoard?> = _board

    private val _screen: MutableState<ScreenState> = mutableStateOf(ScreenState.Loading)
    val screen: State<ScreenState> = _screen

    /**
     * Builds a puzzle based based on a given pgn
     */
    fun buildPuzzle(id: String) {
        viewModelScope.launch {
            try {
                _screen.value = ScreenState.Loading
                val dto = puzzleRepository.getPuzzle(id)
                puzzle = dto
                loadPuzzle()
                _screen.value = ScreenState.Loaded
            } catch (e: Exception) {
                _screen.value = ScreenState.Error
            }
        }
    }

    /**
     * Loads the solution of the new puzzle
     */
    fun loadSolution() {
        val list = puzzle.actualPgn.split(" ")
        val toRotate = puzzle.pgn.split(" ").size % 2 != 0
        _board.value = (Parser(list, toRotate)).parsePGN()
    }

    /**
     * Builds a puzzle based based on a given pgn
     */
    fun loadPuzzle() {
        val list = puzzle.pgn.split(" ")
        _board.value = (Parser(list, list.size % 2 != 0)).parsePGN()
    }
}