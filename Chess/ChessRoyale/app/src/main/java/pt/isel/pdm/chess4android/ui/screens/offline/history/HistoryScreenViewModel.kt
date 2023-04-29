package pt.isel.pdm.chess4android.ui.screens.offline.history

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.isel.pdm.chess4android.dataAccess.PuzzleHistoryDatabase
import pt.isel.pdm.chess4android.dataAccess.PuzzleRepository
import pt.isel.pdm.chess4android.domain.FetchState
import pt.isel.pdm.chess4android.domain.ScreenState
import pt.isel.pdm.chess4android.domain.puzzle.PuzzleHistoryDTO
import javax.inject.Inject

@HiltViewModel
class HistoryScreenViewModel @Inject constructor(
    private val historyDao: PuzzleHistoryDatabase,
    private val puzzleRepository: PuzzleRepository
) : ViewModel() {

    private val _screen: MutableState<ScreenState> = mutableStateOf(ScreenState.Loading)
    val screen: State<ScreenState> = _screen

    private val _history: MutableState<List<PuzzleHistoryDTO>> = mutableStateOf(listOf())
    val history: State<List<PuzzleHistoryDTO>> = _history

    var fetch: FetchState = FetchState.NotLoaded
        private set

    init {
        viewModelScope.launch {
            val quotes = historyDao.getAll()
            _history.value = quotes
            val dto = puzzleRepository.maybeGetTodayPuzzleFromDB()
            fetch = if (dto != null) {
                FetchState.Loaded
            } else {
                FetchState.NotLoaded
            }
            _screen.value = ScreenState.Loaded
        }
    }

    fun fetchDailyPuzzle() {
        viewModelScope.launch {
            try {
                _screen.value = ScreenState.Loading
                puzzleRepository.fetchPuzzleOfDay()
                _history.value = historyDao.getAll()
                fetch = FetchState.Loaded
                _screen.value = ScreenState.Loaded
            } catch (e: Exception) {
                if (puzzleRepository.maybeGetTodayPuzzleFromDB() == null) {
                    fetch = FetchState.NotLoaded
                    _screen.value = ScreenState.Loaded
                }
            }
        }
    }
}