package pt.isel.pdm.chess4android.domain

sealed class FetchState {
    object NotLoaded : FetchState()
    object Loaded : FetchState()
}

sealed class ScreenState {
    object Loading : ScreenState()
    object Loaded : ScreenState()
    object Error : ScreenState()
}