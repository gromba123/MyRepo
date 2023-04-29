package pt.isel.pdm.chess4android.ui.screens.offline.puzzle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pt.isel.pdm.chess4android.dataAccess.PuzzleRepository
import pt.isel.pdm.chess4android.domain.ScreenState
import pt.isel.pdm.chess4android.domain.pieces.Location
import pt.isel.pdm.chess4android.domain.pieces.Piece
import pt.isel.pdm.chess4android.domain.puzzle.MoveState
import pt.isel.pdm.chess4android.domain.puzzle.Parser
import pt.isel.pdm.chess4android.domain.puzzle.PuzzleBoard
import pt.isel.pdm.chess4android.domain.puzzle.PuzzleDTO
import pt.isel.pdm.chess4android.domain.puzzle.PuzzleRuler
import pt.isel.pdm.chess4android.domain.puzzle.PuzzleState
import pt.isel.pdm.chess4android.utils.PaintResults
import pt.isel.pdm.chess4android.views.DrawController
import javax.inject.Inject
import kotlin.collections.HashMap
import kotlin.collections.List
import kotlin.collections.isNotEmpty
import kotlin.collections.reduce
import kotlin.collections.set
import kotlin.collections.toMutableList

private const val ACTIVITY_STATE_PUZZLE_BOARD = "PuzzleActivity.board"
private const val ACTIVITY_STATE_PUZZLE_SLN = "PuzzleActivity.sln"
private const val MOVE_DELAY = 10000L

/**
 * ViewModel for the [PuzzleScreenViewModel]
 */
@HiltViewModel
class PuzzleScreenViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val puzzleRepository: PuzzleRepository,
) : ViewModel() {

    private var puzzleRuler: PuzzleRuler? = state[ACTIVITY_STATE_PUZZLE_SLN]
    private val acquiredMoves: HashMap<Piece, List<Location>> = HashMap()
    private val drawController = DrawController()
    private lateinit var puzzle: PuzzleDTO
    private var selectedPiece: Piece? = null
    private var puzzleBoard = PuzzleBoard()

    private val _board: MutableLiveData<List<List<Piece>>> = MutableLiveData()
    val board: LiveData<List<List<Piece>>> = _board

    private val _puzzleState = MutableLiveData<PuzzleState>()
    val puzzleState = _puzzleState

    private val _paintResults = MutableLiveData(PaintResults())
    val paintResults: LiveData<PaintResults> = _paintResults

    private val _screen: MutableLiveData<ScreenState> = MutableLiveData(ScreenState.Loading)
    val screen: LiveData<ScreenState> = _screen

    private var _actualPuzzleState = PuzzleState.OnProgress

    init {
        if (state.contains(ACTIVITY_STATE_PUZZLE_BOARD)) {
            val b = state.get<PuzzleBoard>(ACTIVITY_STATE_PUZZLE_BOARD)
            if (b != null) {
                puzzleBoard = b
                _board.value = puzzleBoard.board
            }
        }
    }

    /**
     * Moves a piece if it is an allowed move
     */
    fun movePiece(x: Int, y: Int) {
        viewModelScope.launch {
            if (
                puzzleRuler == null ||
                puzzleRuler?.getPgn()?.isEmpty() == true ||
                _actualPuzzleState == PuzzleState.Finished
            ) return@launch
            val locked = selectedPiece
            if (locked != null) {
                selectedPiece = null
                drawController.cleanSelectedPiece()
                _paintResults.value = drawController.getActualResults()
                if (!(locked.location.x == x && locked.location.y == y)) {
                    puzzleRuler?.let {
                        val rulerResult = it.tryExecuteMove(locked.location, Location(x, y), puzzleBoard)
                        if (rulerResult.moveState == MoveState.Allowed) {
                            puzzleBoard = rulerResult.board
                            _board.value = puzzleBoard.board
                            acquiredMoves.clear()
                            val opponentResult = it.executeOpponentMove(puzzleBoard)
                            if (opponentResult.puzzleState == PuzzleState.Finished) {
                                _actualPuzzleState = PuzzleState.Finished
                                delay(MOVE_DELAY)
                                finish()
                            } else {
                                _actualPuzzleState = PuzzleState.Finished
                                puzzleBoard = opponentResult.board
                                save()
                                delay(MOVE_DELAY)
                                _board.value = puzzleBoard.board
                                _actualPuzzleState = PuzzleState.OnProgress
                            }
                            return@launch
                        } else {
                            _paintResults.value = drawController.getActualResults()
                        }
                    }
                } else {
                    _paintResults.value = drawController.getActualResults()
                    return@launch
                }
            }

            val clicked: Piece = puzzleBoard.board[y][x]
            if (clicked.team != puzzleBoard.playingTeam) return@launch
            selectedPiece = clicked
            if (!acquiredMoves.containsKey(clicked)) {
                val moves = clicked.getMoves(
                    puzzleBoard.board,
                    puzzleBoard.getKing(puzzleBoard.playingTeam)
                )
                acquiredMoves[clicked] = moves
            }
            _paintResults.value = drawController.drawSelectedPiece(
                clicked.location,
                acquiredMoves[clicked]!!
            )
        }
    }

    /**
     * Initiates the loading the initial puzzle
     */
    fun initPuzzle(id: String) {
        if (!state.contains(ACTIVITY_STATE_PUZZLE_BOARD)) {
            loadPuzzleFromBD(id)
        }
    }

    /**
     * Returns a [LiveData] that loads a puzzle from the local DB
     */
    private fun loadPuzzleFromBD(id: String) {
        viewModelScope.launch {
            try {
                _screen.value = ScreenState.Loading
                puzzle = puzzleRepository.getPuzzle(id)
                loadPuzzle(puzzle)
                _screen.value = ScreenState.Loaded
            } catch (e: Exception) {
                _screen.value = ScreenState.Error
            }
        }
    }

    /**
     * Loads the puzzle based on the current state.
     */
    private fun loadPuzzle(puzzle: PuzzleDTO) {
        val pgn: List<String>
        val sln: List<String>
        if (puzzle.actualSln.isNotEmpty() && puzzle.actualSln != puzzle.sln) {
            pgn = puzzle.actualPgn.split(" ")
            sln = puzzle.actualSln.split(" ")
        } else {
            pgn = puzzle.pgn.split(" ")
            sln = puzzle.sln.split(" ")
        }
        puzzleBoard = (Parser(pgn, puzzle.pgn.split(" ").size % 2 != 0)).parsePGN()
        _board.value = puzzleBoard.board
        puzzleRuler =
            PuzzleRuler(pgn.toMutableList(), sln.toMutableList(), puzzleBoard.playingTeam, puzzle.id)
    }

    /**
     * Restarts the current puzzle
     */
    fun restart() {
        val pgn = puzzle.pgn.split(" ")
        val sln = puzzle.sln.split(" ")
        puzzleBoard = (Parser(pgn, pgn.size % 2 != 0)).parsePGN()
        _board.value = puzzleBoard.board
        puzzleRuler = PuzzleRuler(
            pgn.toMutableList(),
            sln.toMutableList(),
            puzzleBoard.playingTeam,
            puzzle.id
        )
        save()
    }

    /**
     * Saves the current puzzle state into the Room DB
     */
    private fun save() {
        writeInBD(1) {}
    }

    /**
     * Saves the current puzzle state into the Room DB
     * and finishes the puzzle
     */
    private fun finish() {
        writeInBD(2) {
            _puzzleState.value = PuzzleState.Finished
        }
    }

    /**
     * Writes the puzzle actual state into the Room DB
     */
    private fun writeInBD(state: Int, action: () -> Unit) {
        puzzleRuler?.let {
            viewModelScope.launch {
                val pgn = it.getPgn().reduce { acc, s -> "$acc $s" }
                val sln = if (it.getSln().isNotEmpty())
                    it.getSln().reduce { acc, s -> "$acc $s" }
                else ""
                puzzleRepository.updatePuzzle(it.id, pgn, sln, state)
                action()
            }
        }
    }

    /**
     * Saves the game state when the ViewModel is about to be destroyed. It's saved when the
     * ViewModel is finished by normal termination even not being necessary but avoids
     * the game state to be saved in every move.
     */
    override fun onCleared() {
        super.onCleared()
        state[ACTIVITY_STATE_PUZZLE_BOARD] = puzzleBoard
        state[ACTIVITY_STATE_PUZZLE_SLN] = puzzleRuler
    }
}