package pt.isel.pdm.chess4android.ui.screens.offline.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    /**
     * Holds a [LiveData] with the list of quotes
     */
    private val _history: MutableLiveData<List<PuzzleHistoryDTO>> = MutableLiveData()
    val history: LiveData<List<PuzzleHistoryDTO>> = _history

    private val _fetch: MutableLiveData<FetchState> = MutableLiveData(FetchState.Loading)
    val fetch: LiveData<FetchState> = _fetch

    private val _screen: MutableLiveData<ScreenState> = MutableLiveData(ScreenState.Loading)
    val screen: LiveData<ScreenState> = _screen

    init {
        viewModelScope.launch {
            val quotes = historyDao.getAll()
            _history.value = quotes
            _screen.value = ScreenState.Loaded
        }

        viewModelScope.launch {
            val dto = puzzleRepository.maybeGetTodayPuzzleFromDB()
            if (dto != null) {
                _fetch.value = FetchState.Loaded
            } else {
                _fetch.value = FetchState.NotLoaded
            }
        }
    }

    fun fetchDailyPuzzle() {
        viewModelScope.launch {
            try {
                _fetch.value = FetchState.Loading
                puzzleRepository.fetchPuzzleOfDay()
                val quotes = historyDao.getAll()
                _history.value = quotes
                _fetch.value = FetchState.Loaded
            } catch (e: Exception) {
                if (puzzleRepository.maybeGetTodayPuzzleFromDB() == null) {
                    _fetch.value = FetchState.NotLoaded
                }
            }
        }
    }
}