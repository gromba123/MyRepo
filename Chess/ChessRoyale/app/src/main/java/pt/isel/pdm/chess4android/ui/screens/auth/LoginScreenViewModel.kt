package pt.isel.pdm.chess4android.ui.screens.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pt.isel.pdm.chess4android.domain.ScreenState
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(

) : ViewModel(){
    private val _screenState = mutableStateOf(ScreenState.Loaded)
    val screenState: State<ScreenState> = _screenState

    fun login(
        email: String,
        password: String,
        onComplete: () -> Unit
    ) {
        onComplete()
    }
}