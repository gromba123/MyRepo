package pt.isel.pdm.chess4android.ui.screens.offline.puzzle

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.isel.pdm.chess4android.dataAccess.PuzzleHistoryDatabase
import pt.isel.pdm.chess4android.domain.pieces.Location
import pt.isel.pdm.chess4android.domain.pieces.Piece
import pt.isel.pdm.chess4android.domain.puzzle.*
import pt.isel.pdm.chess4android.utils.DrawResults
import pt.isel.pdm.chess4android.views.DrawController
import javax.inject.Inject

private const val ACTIVITY_STATE_PUZZLE_BOARD = "PuzzleActivity.board"
private const val ACTIVITY_STATE_PUZZLE_SLN = "PuzzleActivity.sln"

/**
 * ViewModel for the [PuzzleActivityViewModel]
 */
@HiltViewModel
class PuzzleActivityViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val historyDao: PuzzleHistoryDatabase,
) : ViewModel() {

    private val _paint = MutableLiveData<DrawResults?>()
    private val _board: MutableLiveData<PuzzleBoard> = MutableLiveData()
    private val _puzzleState = MutableLiveData<PuzzleState>()
    private var puzzleRuler: PuzzleRuler? = state[ACTIVITY_STATE_PUZZLE_SLN]
    private val acquiredMoves: HashMap<Piece, List<Location>> = HashMap()
    private var puzzleBoard = PuzzleBoard()
    private var selectedPiece: Piece? = null
    private val drawController = DrawController()

    val board: LiveData<PuzzleBoard> = _board
    val puzzleState = _puzzleState
    val paint = _paint

    init {
        if (state.contains(ACTIVITY_STATE_PUZZLE_BOARD)) {
            val b = state.get<PuzzleBoard>(ACTIVITY_STATE_PUZZLE_BOARD)
            if (b != null) {
                puzzleBoard = b
                _board.value = puzzleBoard
            }
        }
    }

    /**
     * Moves a piece if it is an allowed move
     */
    fun movePiece(x: Int, y: Int) {
        if (puzzleRuler == null || puzzleRuler?.getPgn()?.isEmpty() == true) return
        val locked = selectedPiece
        if (locked != null) {
            _paint.value = drawController.drawSelectedPiece(null)
            _paint.value = drawController.drawHighlight(null)
            selectedPiece = null

            if (!(locked.location.x == x && locked.location.y == y)) {
                puzzleRuler?.let {
                    val rulerResult = it.tryExecuteMove(locked.location, Location(x, y), puzzleBoard)
                    if (rulerResult.moveState == MoveState.Allowed) {
                        puzzleBoard = rulerResult.board
                        _board.value = puzzleBoard
                        acquiredMoves.clear()
                        if (rulerResult.puzzleState == PuzzleState.Finished) {
                            finish()
                        } else {
                            save()
                        }
                        return
                    }
                }
            } else {
                return
            }
        }

        val clicked: Piece = puzzleBoard.board[y][x]
        if (clicked.team != puzzleBoard.playingTeam) return
        selectedPiece = clicked
        _paint.value = drawController.drawSelectedPiece(clicked.location)
        if (!acquiredMoves.containsKey(clicked)) {
            val moves = clicked.getMoves(
                puzzleBoard.board,
                puzzleBoard.getKing(puzzleBoard.playingTeam)
            )
            acquiredMoves[clicked] = moves
        }
        _paint.value = drawController.drawHighlight(acquiredMoves[clicked])
    }

    /**
     * Restarts the current puzzle
     */
    fun restart(puzzle: PuzzleDTO) {
        val pgn = puzzle.pgn.split(" ")
        val sln = puzzle.sln.split(" ")
        puzzleBoard = (Parser()).parsePGN(pgn, pgn.size % 2 != 0)
        _board.value = puzzleBoard
        puzzleRuler = board.value?.let { it1 ->
            PuzzleRuler(pgn.toMutableList(), sln.toMutableList(), it1.playingTeam, puzzle.id)
        }
        save()
    }

    /**
     * Initiates the loading the initial puzzle
     */
    fun initPuzzle(puzzle: PuzzleDTO) {
        if (!state.contains(ACTIVITY_STATE_PUZZLE_BOARD)) {
            loadPuzzle(puzzle)
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
        puzzleBoard = (Parser()).parsePGN(pgn, puzzle.pgn.split(" ").size % 2 != 0)
        _board.value = puzzleBoard
        puzzleRuler = board.value?.let { it1 ->
            PuzzleRuler(pgn.toMutableList(), sln.toMutableList(), it1.playingTeam, puzzle.id)
        }
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
                historyDao.update(it.id, pgn, sln, state)
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