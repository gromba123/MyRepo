package pt.isel.pdm.chess4android.ui.screens.auth.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pt.isel.pdm.chess4android.domain.ScreenState
import javax.inject.Inject

@HiltViewModel
class SignupScreenViewModel @Inject constructor(

) : ViewModel() {
    private val _screenState = mutableStateOf(ScreenState.Loaded)
    val screenState: State<ScreenState> = _screenState

    fun signup(
        email: String,
        username: String,
        password: String,
        onComplete: () -> Unit
    ) {
        onComplete()
    }
}